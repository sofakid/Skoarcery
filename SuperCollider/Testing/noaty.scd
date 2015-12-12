
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((
	
	low: [
		"_c) _d) _e)    _f) _g) _a) _b)",
		[(note: 0), (note: 2), (note: 4),    (note: 5), (note: 7), (note: 9), (note: 11)]
	],

	high: [
		"c) d) e)    f) g) a) b)",
		[(note: 12), (note: 14), (note: 16),    (note: 17), (note: 19), (note: 21), (note: 23)]
	],

	flats_low: [
		"_cb) _db) _eb)   _fb) _gb) _ab)   _bb ) _b )",
		[(note: -1), (note: 1), (note: 3),   (note: 4), (note: 6), (note: 8),   (note: 10), (note: 11)]
	],

	flats_high: [
		"cb) db) eb)   fb) gb) ab)   bb ) b )",
		[(note: 11), (note: 13), (note: 15),   (note: 16), (note: 18), (note: 20),   (note: 22), (note: 23)]
	],
	
	sharps_low: [
		"_c#) _d#) _e#)   _f#) _g#) _a#)   _b# ) _b )",
		[(note: 1), (note: 3), (note: 5),   (note: 6), (note: 8), (note: 10),   (note: 12), (note: 11)]
	],

	sharps_high: [
		"c#) d#) e#)  f#) g#) a#)  b#) b)",
		[(note: 13), (note: 15), (note: 17),   (note: 18), (note: 20), (note: 22),   (note: 24), (note: 23)]
	],

	choards_basic: [
		"C) D)   E) F)   G) A)   B)",
		[   (note: [0,4,7]), (note: [2,6,9]),
			(note: [4,8,11]), (note: [5,9,12]),
			(note: [7,11,14]), (note: [9,13,16]),
			(note: [11,15,18])]
	],

 	choards_moar: [
		"Cm) C7) Cm7) CM7) CmM7)   Cdim) Csus2) Csus4M7)  Caug) Caug6)",
		[   (note: [0, 3, 7]),
			(note: [0, 4, 7, 10]),
			(note: [0, 3, 7, 10]),
			(note: [0, 4, 7, 11]),
			(note: [0, 3, 7, 11]),
			
			(note: [0, 3, 6]),

			(note: [0, 2, 7]),
			(note: [0, 5, 7, 11]), // c f g b

			(note: [0, 4, 8]),
			(note: [0, 4, 8, 9]),
		]
	],

));
