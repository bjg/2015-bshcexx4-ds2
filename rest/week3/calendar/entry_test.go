package calendar

import (
	"testing"
	"time"

	"github.com/stretchr/testify/assert"
)

var (
	hour             time.Duration
	starts, finishes time.Time
)

func init() {
	hour, _ = time.ParseDuration("1h")
	starts = time.Now()
	finishes = starts.Add(hour)
}

func makeEntry() Entry {
	return Entry{
		title:    "Test Entry",
		starts:   starts,
		finishes: finishes,
	}
}

func TestCreate(t *testing.T) {
	e := makeEntry()
	assert.Equal(t, "Test Entry", e.title)
	assert.Equal(t, starts, e.starts)
	assert.Equal(t, finishes, e.finishes)
}

func TestDuration(t *testing.T) {
	e := makeEntry()
	assert.Equal(t, hour, e.Duration())
}

func TestAdd(t *testing.T) {
	e := makeEntry()
	e = Add(e)
	e = Add(e)
	assert.Equal(t, 2, Count())
}
