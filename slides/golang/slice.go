package main

import (
    "fmt"
)

func main() {
    // START OMIT
    a := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    fmt.Println( a[0] )        // First element
    fmt.Println( a[len(a)-1] ) // Last element
    fmt.Println( a[:4] )       // First four elements
    fmt.Println( a[4:] )       // Last six elements
    fmt.Println( a[2:4] )      // Third and fourth elements
    // Iterate over the elements of a slice
    for n := range a {
        fmt.Print( n, ", " )
    }
    // Append an element
    a = append(a, 11)
    // Remove an element
    a = append(a[:4], a[5:]...)
    fmt.Println( "\n", a[:] )
    // END OMIT
}
