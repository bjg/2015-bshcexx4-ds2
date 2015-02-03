package main

import (
	"fmt"
	"strconv"
)

// START OMIT
func main() {
	fmt.Println(display(getNumber))
}

func display(getNumber func() int) string {
	return "The number is " + strconv.Itoa(getNumber())
}

func getNumber() int {
	return 42;
}
// END OMIT

