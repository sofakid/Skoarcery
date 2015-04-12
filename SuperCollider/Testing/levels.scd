
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((
	
	ppp: [
		"ppp ) pp ) p ) mp )",
		[(amp: 1/8), (amp: 2/8), (amp: 3/8), (amp: 4/8)]
	],

	pppiano: [
		"pppiano ) ppiano ) piano ) mezzopiano ) mpiano )",
		[(amp: 1/8), (amp: 2/8), (amp: 3/8), (amp: 4/8), (amp: 4/8)]
	],

	fff: [
		"fff ) ff ) forte ) mf )",
		[(amp: 1), (amp: 7/8), (amp: 6/8), (amp: 5/8)]
	],

	ffforte: [
		"ffforte ) fforte ) forte ) mforte ) mezzoforte )",
		[(amp: 1), (amp: 7/8), (amp: 6/8), (amp: 5/8), (amp: 5/8)]
	],

	hashbars_short: [
		"[] =>@x) [ ] =>@x) [#] =>@x) [  ] =>@x) [# ] =>@x) [##] =>@x) [#  ] =>@x) [## ] =>@x) [###] =>@x) ",
		[(x: 0),  (x: 0),   (x: 1),   (x: 0),    (x: 1/2),  (x: 1),    (x: 1/3),   (x: 2/3),   (x: 1),   ]
	],

	hashbars_longer: [
		"[#####      ] =>@x) [############################## ] =>@x) [############################################################ ] =>@x)",
		[(x: 5/11),          (x: 30/31),                             (x: 60/61),   ]
	],

));
