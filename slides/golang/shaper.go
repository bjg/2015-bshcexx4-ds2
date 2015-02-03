package main

import (
    "fmt"
    "math"
)

// START OMIT
type Shaper interface {
    Area() float32
}
type Rect struct {
    width, height float32
}
type Circle struct {
    radius float32
}
func main() {
    r, c := &Rect{width: 10, height: 5}, &Circle{radius: 10}
    Display(r, c)
}
func (r *Rect) Area() float32 {
    return r.width * r.height
}
func (c *Circle) Area() float32 {
    return math.Pi * c.radius * c.radius
}
func Display(ss ...Shaper) {               // Example of variadic argument passing
    for _, s := range ss {
        fmt.Println("area: ", s.Area())    // Iterate over arguments
    }
}
// END OMIT
