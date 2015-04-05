
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((

	math_add_a: [
		"1 + 2 ) 3 + 4 + 5 )",
		[(\degree:3),(\degree:12)]
	],

	math_add_b: [
		"1 + 2 ) 3 + 4 + 5 ) $ + 3 )",
		[(\degree:3),(\degree:12),(\degree:15)]
	],

	math_add_c: [
		"0 + 0 ) 1 + 1 + 1 => @x )",
		[(\degree:0),(\degree:3)]
	],

	math_mul_a: [
		"1 x 2 ) 3 x 4 x 5 )",
		[(\degree:2),(\degree:60)]
	],

	math_mul_b: [
		"1 + 2 x 5 ) 3 + 4 x 5 ) $ x 2 + 1 )",
		[(\degree:15),(\degree:35),(\degree:71)]
	]

));
