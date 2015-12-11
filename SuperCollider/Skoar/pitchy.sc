// -------------------
// Pitchy Skoarpuscles
// -------------------
SkoarpuscleKey : Skoarpuscle {
	
	var <scale_name;
	var <scale;
	var <root;

	init {
		| choard |
		root = 0;
		scale_name = nil;

		if (choard.isKindOf(SkoarpuscleList)) {
			var a = choard.val;

			if (a.size > 0) {
				var x = a[0];
			
				if (x.isKindOf(SkoarpuscleChoard)) {
					choard = x;
				}; 
				
				if (a.size > 1) {
					var y = a[1];
					if (y.isKindOf(SkoarpuscleSymbol)) {
						scale_name = y.val;
					};
				};
				
			};
			

		};

		if (choard.isKindOf(SkoarpuscleChoard)) {
			var minor;

			root = switch (choard.letter)
				{"C"} {0}
				{"D"} {2}
				{"E"} {4}
				{"F"} {5}
				{"G"} {7}
				{"A"} {-1}
				{"B"} {-3} + choard.sharps;

			minor = choard.lexeme.findRegexp("m")[0];
				
			if (minor.notNil and: scale_name.isNil) {
				scale_name = \minor;
			};

		};

		if (scale_name.isNil) {
			scale_name = \major;
		};
		scale = Scale.all[scale_name];
	}

	apply {
		| event |
	
		event[\scale] = scale;
		event[\root] = root;

		^event;	
	}
}

SkoarpuscleNoat : Skoarpuscle {

    var <lexeme;
    var <low;
    var <sharps;

    init {
        | lex |

        var noat_regex = "^(_?)([a-g])";
        var sharps_regex = "[a-g](#*|b*)$"; //emacs is unhappy if i don't put =>"
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
            {"d"} {2}
            {"e"} {4}
            {"f"} {5}
            {"g"} {7}
            {"a"} {9}
            {"b"} {11};

        case {sharps > 0} {
            x = x + 1;
        } {sharps < 0} {
			x = x - 1;
		};
		
        if (low == false) {
            x = x + 12;
        };

        val = x;
    }

    flatten {^val;}

    isNoatworthy { ^true; }

    asNoat {
        ^SkoarNoat_Note(val);
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
	 var <letter;

	 init {
        | lex |

		// lexeme was matched by: (D(?![a.])|[ABCEFG])(#|b)?([Mm0-9]|sus|dim)*
        var noat_regex = "^~*([A-G])";
        var sharps_regex = "^~*[A-G](#|b)?";
        var s = lex;
        var r = s.findRegexp(noat_regex);
        var x = -1;

		var first, third, fifth;
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
            {"D"} {2}
            {"E"} {4}
            {"F"} {5}
            {"G"} {7}
            {"A"} {9}
            {"B"} {11};

        case {sharps > 0} {
            x = sharps + x;
        } {sharps < 0} {
            x = x - sharps;
        };

		 first = x;
		 third = x + 4;
		 fifth = x + 7;

		 if (lex.findRegexp("[^i]m").size != 0) {
			 third = third - 1;
		 };

		 if (lex.findRegexp("sus2").size != 0) {
			 third = third - 2;
		 };

		 if (lex.findRegexp("sus4").size != 0) {
			 third = third + 1;
		 };

		 if (lex.findRegexp("dim").size != 0) {
			 third = third - 1;
			 fifth = fifth - 1;
		 };

		 if (lex.findRegexp("aug").size != 0) {
			 fifth = fifth + 1
		 };

		 val = [first, third, fifth];
		 
		 if (lex.findRegexp("M7").size != 0) {
			 val = val.add(first + 11);
		 } {
			 if (lex.findRegexp("7").size != 0) {
				 val = val.add(first + 10);
			 }
		 };

		 if (lex.findRegexp("aug6").size != 0) {
			 val = val.add(fifth + 1); // fifth has already been incremented
		 };

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

    isNoatworthy { ^true; }

    asNoat {
        ^this;
    }

	execute {
		| minstrel |
        //"SkoarNoat.execute: ".post; key.post; val.postln;
        minstrel.koar[\choard] = val;
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

				// this is wrong
                //val = val.add(y.val.cpsmidi);
				val = val.add(0);
				
            } {y.isKindOf(SkoarNoat_DegreeList)} {
                y.val.do {
                    | z |
                    val = val.add(z);
                };

            } {y.isKindOf(SkoarNoat_FreqList)} {
                y.val.do {
                    | z |
					val = val.add(0);
                    //val = val.add(z.cpsmidi);
                };
            };
        };
    }
}

SkoarNoat_Note : SkoarNoat {

    init {
        | x |
        key = \note;
        val = x;
    }
}

SkoarNoat_NoteList : SkoarNoat {

    init {
        | x |
        key = \note;
        val = List.new(x.size());

        x.do {
            | y |

            case {y.isKindOf(SkoarNoat_Note)} {
                val = val.add(y.val);

            } {y.isKindOf(SkoarNoat_Freq)} {

				// this is wrong
                //val = val.add(y.val.cpsmidi);
				val = val.add(0);
				
            } {y.isKindOf(SkoarNoat_NoteList)} {
                y.val.do {
                    | z |
                    val = val.add(z);
                };

            } {y.isKindOf(SkoarNoat_FreqList)} {
                y.val.do {
                    | z |
					val = val.add(0);
                    //val = val.add(z.cpsmidi);
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
                //val = val.add(y.val.midicps);

            } {y.isKindOf(SkoarNoat_Freq)} {
                val = val.add(y.val);

            } {y.isKindOf(SkoarNoat_DegreeList)} {
                y.val.do {
                    | z |
                    //val = val.add(z.midicps);
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