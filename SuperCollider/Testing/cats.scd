
var qrt = (\dur:1);
var eth = (\dur:1/2);
var sxt = (\dur:1/4);

SkoarTestRunner((

    cats_eat_values: [
        "2 => @two ) =^.^= => @two ) < =^.^= > => @two )",
        [(\two:2),(\two:inf),(\two:[nil])]
    ],

	cats_regex_ambiguity: [
        "<=^.^=,=^.^=> => @foo )",
        [(\foo:[nil,nil])]
    ],


));
