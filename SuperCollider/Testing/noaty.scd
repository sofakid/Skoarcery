
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((
	
	low: [
		"_c) _d) _e) _f) _g) _a) _b)",
		[(degree: 0), (degree: 1), (degree: 2), (degree: 3), (degree: 4), (degree: 5), (degree: 6)]
	],

	high: [
		"c) d) e) f) g) a) b)",
		[(degree: 7), (degree: 8), (degree: 9), (degree: 10), (degree: 11), (degree: 12), (degree: 13)]
	],

	flats_low: [
		"_cb) _db) _eb)   _fb) _gb) _ab)   _bb ) _b )",
		[(degree: -0.1), (degree: 0.1), (degree: 1.1),   (degree: 2.1), (degree: 3.1), (degree: 4.1),   (degree: 5.1), (degree: 6)]
	],

	flats_high: [
		"cb) db) eb) fb) gb) ab) bb ) b )",
		[(degree: 6.1), (degree: 7.1), (degree: 8.1),   (degree: 9.1), (degree: 10.1), (degree: 11.1),   (degree: 12.1), (degree: 13)]
	],
	
	sharps_low: [
		"_c#) _d#) _e#) _f#) _g#) _a#) _b# ) _b )",
		[(degree: 0.1), (degree: 1.1), (degree: 2.1),   (degree: 3.1), (degree: 4.1), (degree: 5.1),   (degree: 6.1), (degree: 6)]
	],

	sharps_high: [
		"c#) d#) e#) f#) g#) a#) b#) b)",
		[(degree: 7.1), (degree: 8.1), (degree: 9.1),   (degree: 10.1), (degree: 11.1), (degree: 12.1),   (degree: 13.1), (degree: 13)]
	],

	

));
