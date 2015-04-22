
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((

	da_capo_a: [
		") ) ) fine ] ] D.C. al fine",
		[
		qrt, qrt, qrt, eth, eth,
		qrt, qrt, qrt
		]
	],

	da_capo_b: [
		") ) ) |: ] ] :| fine D.C. al fine",
		[
		qrt, qrt, qrt, eth, eth, eth, eth,
		qrt, qrt, qrt, eth, eth, eth, eth
		]
	],

	// next is unimplemented
	listy_f: [
		"<3,4> => @x <0, !x.next, !x.next, 2> => @food )",
		[( 'dur': 1.0, 'food': [ 0, 3, 4, 2 ], 'x':[3, 4] )]
	],

	// right now it's popping the skoar off, and forgetting that it has seen the colon
	colons_unbalanced: [
		"| ) ) ) :| ] ] :|",
		[
		qrt, qrt, qrt,
		qrt, qrt, qrt, eth, eth,
		qrt, qrt, qrt, eth, eth,
		]
	],

	argspec_ambiguity: [
	   "{! <a,c,e> ) !}",
	   [qrt]
	],

));
