package calendar

import (
	"time"
)

var (
	entries = make(map[int]Entry)
	index   int
)

type Entry struct {
	ID       int
	Title    string
	Starts   time.Time
	Finishes time.Time
}

func (e Entry) Duration() time.Duration {
	return e.Finishes.Sub(e.Starts)
}

func Lookup(id int) (Entry, bool) {
	e, isPresent := entries[id]
	return e, isPresent
}

func Add(e Entry) Entry {
	index++
	e.ID = index
	Update(e)
	return e
}

func Update(e Entry) {
	entries[e.ID] = e
}

func Remove(id int) {
	delete(entries, id)
}

func Count() int {
	return len(entries)
}

func All() []Entry {
	all := []Entry{}
	for _, e := range entries {
		all = append(all, e)
	}
	return all
}
