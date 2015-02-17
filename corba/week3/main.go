package main

import (
	"encoding/json"
	"net/http"
	"strconv"
	"time"

	"github.com/codegangsta/negroni"
	"github.com/gorilla/pat"

	"./calendar"
)

/*
 * Our api is as follows:
 *
 * GET /entries
 * GET /entries/:id
 * POST /entries
 * PUT /entries/:id
 * DELETE /entries/:id
 */

func main() {
	addTestEntries()

	r := pat.New()
	n := negroni.Classic()

	r.Get("/entries/{id}", func(res http.ResponseWriter, req *http.Request) {
		id, _ := strconv.Atoi(req.URL.Query().Get(":id"))
		if e, isPresent := calendar.Lookup(id); isPresent {
			json, _ := json.Marshal(e)
			res.Header().Set("Content-Type", "application/json; charset=utf-8")
			res.Write(json)
		} else {
			res.WriteHeader(http.StatusNotFound)
		}
	})

	r.Get("/entries", func(res http.ResponseWriter, req *http.Request) {
		res.Header().Set("Content-Type", "application/json; charset=utf-8")
		json, _ := json.Marshal(calendar.All())
		res.Write(json)
	})

	r.Post("/entries", func(res http.ResponseWriter, req *http.Request) {
		decoder := json.NewDecoder(req.Body)
		e := calendar.Entry{}
		decoder.Decode(&e)
		calendar.Add(e)
		res.WriteHeader(http.StatusCreated)
	})

	r.Put("/entries/{id}", func(res http.ResponseWriter, req *http.Request) {
		id, _ := strconv.Atoi(req.URL.Query().Get(":id"))
		if _, isPresent := calendar.Lookup(id); isPresent {
			decoder := json.NewDecoder(req.Body)
			e := calendar.Entry{}
			if err := decoder.Decode(&e); err == nil {
				calendar.Update(e)
				res.Header().Set("Content-Type", "application/json; charset=utf-8")
				json, _ := json.Marshal(e)
				res.Write(json)
			} else {
				res.WriteHeader(http.StatusBadRequest)
			}
		} else {
			res.WriteHeader(http.StatusNotFound)
		}
	})

	r.Delete("/entries/{id}", func(res http.ResponseWriter, req *http.Request) {
		id, _ := strconv.Atoi(req.URL.Query().Get(":id"))
		if _, isPresent := calendar.Lookup(id); isPresent {
			calendar.Remove(id)
			res.WriteHeader(http.StatusOK)
		} else {
			res.WriteHeader(http.StatusNotFound)
		}
	})

	n.UseHandler(r)
	n.Run(":8080")
}

func addTestEntries() {
	hour, _ := time.ParseDuration("1h")
	starts := time.Now()
	finishes := starts.Add(hour)
	calendar.Add(calendar.Entry{
		Title:    "Test Entry",
		Starts:   starts,
		Finishes: finishes,
	})
}
