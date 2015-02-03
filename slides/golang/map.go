package main

import (
    "fmt"
)

func main() {
    // START OMIT
    // A map of string keywords to integer valuesi
    ages := map[string]int{
       "Janice": 19,
    }
    ages["John"] = 20
    ages["Jane"] = 21

    // Iterate over the elements of a map
    for name, age := range ages {
        fmt.Println(name + " is", age)
    } 

    _, available := ages["Joe"]
    fmt.Println("Joe's age availability:", available)
    // END OMIT
}
