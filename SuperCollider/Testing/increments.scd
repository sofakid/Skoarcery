
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((
	
	incr_int: [
		"1 => @x ) 1 +> @x ) 2 +> @x ) 5 +> @x )",
		[(x: 1), (x: 2), (x: 4), (x: 9)]
	],

	decr_int: [
		"1 => @x ) 1 -> @x ) 2 -> @x ) 5 -> @x )",
		[(x: 1), (x: 0), (x: -2), (x: -7)]
	],

));
