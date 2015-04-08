// -------------------
// Pitchy Skoarpuscles
// -------------------
SkoarpuscleNoat : Skoarpuscle {

    var <lexeme;
    var <low;
    var <sharps;

    init {
        | lex |

        var noat_regex = "^(_?)([a-g])";
        var sharps_regex = "[a-g](#*|b*)$";
        var s = lex;
        var r = s.findRegexp(noat_regex);
        var x = -1;
        var letter;

		lexeme = lex;

        low = r[1][1] != "";
        letter = r[2][1];

        r = s.findRegexp(sharps_regex);
        s = r[1][1];

        if (s.beginsWith("#")) {
            x = 1;
        };

        sharps = s.size * x;

        x = switch (letter)
            {"c"} {0}
            {"d"} {1}
            {"e"} {2}
            {"f"} {3}
            {"g"} {4}
            {"a"} {5}
            {"b"} {6};

        case {sharps > 0} {
            x = x + 0.1;
        } {sharps < 0} {
			x = -0.9 + x;
		};

		if (x == -0.9) {
			x = -0.1;
		};
		
        if (low == false) {
            x = x + 7;
        };

		// wow
		if (x == 6.9) {
			x = 6.1;
		};

        val = x;
    }

    flatten {^val;}

    isNoatworthy { ^true; }

    asNoat {
        ^SkoarNoat_Degree(val);
    }

	asString {
		^lexeme;
	}

	asSymbol {
		^lexeme.asSymbol;
	}

	raiseBy {
		| x |
		("should raise " ++ lexeme ++ " by " ++ x).postln;
	}

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

}


SkoarpuscleChoard : Skoarpuscle {
     
	 var <sharps;
	 var <lexeme;

	 init {
        | lex |

		// lexeme was matched by: (D(?![a.])|[ABCEFG])(#|b)?([Mm0-9]|sus|dim)*
        var noat_regex = "^([A-G])";
        var sharps_regex = "[A-G](#|b)?";
        var s = lex;
        var r = s.findRegexp(noat_regex);
        var x = -1;
        var letter;

		lexeme = lex;
        letter = r[1][1];

        r = s.findRegexp(sharps_regex);
        s = r[1][1];

        if (s.beginsWith("#")) {
            x = 1;
        };

        sharps = s.size * x;

        x = switch (letter)
            {"C"} {0}
            {"D"} {1}
            {"E"} {2}
            {"F"} {3}
            {"G"} {4}
            {"A"} {5}
            {"B"} {6};

        case {sharps > 0} {
            x = sharps * 0.1 + x;
        } {sharps < 0} {
            x = sharps * -0.1 + x - 1;
        };

        val = x;
    }

    asString {
		^lexeme;
	}

	asSymbol {
		^lexeme.asSymbol;
	}

	raiseBy {
		| x |
		("should raise " ++ lexeme ++ " by " ++ x).postln;
	}

    flatten {^lexeme;}

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

}

SkoarNoat {
    var <key;
    var <val;

    *new {
        | x |
        ^super.new.init(x);
    }

    execute {
        | minstrel |
        //"SkoarNoat.execute: ".post; key.post; val.postln;
        minstrel.koar[key] = val;
    }
}

SkoarNoat_Freq : SkoarNoat {

    init {
        | x |
        key = \freq;
        val = x;
    }
}

SkoarNoat_Degree : SkoarNoat {

    init {
        | x |
        key = \degree;
        val = x;
    }
}

SkoarNoat_DegreeList : SkoarNoat {

    init {
        | x |
        key = \degree;
        val = List.new(x.size());

        x.do {
            | y |

            case {y.isKindOf(SkoarNoat_Degree)} {
                val = val.add(y.val);

            } {y.isKindOf(SkoarNoat_Freq)} {
                val = val.add(y.val.cpsmidi);

            } {y.isKindOf(SkoarNoat_DegreeList)} {
                y.val.do {
                    | z |
                    val = val.add(z);
                };

            } {y.isKindOf(SkoarNoat_FreqList)} {
                y.val.do {
                    | z |
                    val = val.add(z.cpsmidi);
                };
            };
        };
    }
}

SkoarNoat_FreqList : SkoarNoat {

    init {
        | x |
        key = \freq;
        val = List.new(x.size());

        x.do {
            | y |

            case {y.isKindOf(SkoarNoat_Degree)} {
                val = val.add(y.val.midicps);

            } {y.isKindOf(SkoarNoat_Freq)} {
                val = val.add(y.val);

            } {y.isKindOf(SkoarNoat_DegreeList)} {
                y.val.do {
                    | z |
                    val = val.add(z.midicps);
                };

            } {y.isKindOf(SkoarNoat_FreqList)} {
                y.val.do {
                    | z |
                    val = val.add(z);
                };
            };

        };
    }
}