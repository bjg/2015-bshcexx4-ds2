package main

import "fmt"

type Rect struct {
    width, height float32
}

func main() {
    // START OMIT
    r := &Rect{width: 10, height: 5}
    fmt.Println("area: ", r.Area())
    // END OMIT
}

func (r *Rect) Area() float32 {
    return r.width * r.height
}
