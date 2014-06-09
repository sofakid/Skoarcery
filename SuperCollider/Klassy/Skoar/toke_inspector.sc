// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = Dictionary[
        
            "Toke_Int" -> {
                | toke |
                toke.val = toke.lexeme.asInteger;
            },
            
            "Toke_Float" -> {
                | toke |
                toke.val = toke.lexeme.asFloat;
            },
            
            "Toke_Carrots" -> {
                | toke |
                toke.val = toke.lexeme.size;
            },
            
            "Toke_Tuplet" -> {
                | toke |
                toke.val = 0;
            },

            "Toke_DynPiano" -> {
                | toke |
                toke.val = 0;
            },

            "Toke_DynForte" -> {
                | toke |
                toke.val = 0;
            },

            "Toke_VectorNoat" -> {
                | toke |
            
                var vector_noat_regex = "[a-g]";
                var s = toke.lexeme;
                var r = s.findRegexp(vector_noat_regex);

                toke.val = r[0][1];

            },
            
            "Toke_BooleanOp" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_Choard" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_MsgName" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_MsgNameWithArgs" -> {
                | toke |
                var x = "<";
                var s = toke.lexeme;

                if (s.last == x) {
                    s = s.copyRange(0, s.size - 2);
                };

                toke.val = s;
            },
            
            "Toke_Volta" -> {
                | toke |
                toke.val = int(toke.lexeme.strip("[.]"));
            },
            
            "Toke_Symbol" -> {
                | toke |
                toke.val = toke.lexeme[1..].asSymbol;
            },
            
            "Toke_Segno" -> {
                | toke |
                var a = toke.lexeme.split("_");
                if (a.size > 1) {
                    toke.label = a[1];
                } {
                    toke.label = "";
                };
            },
            
            "Toke_String" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_Bars" -> {
                | toke |
                toke.pre_repeat = toke.lexeme.beginsWith(":");
                toke.post_repeat = toke.lexeme.endsWith(":");
                toke.unspent = true;
            },

            // rests
            "Toke_Crotchets" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = true;
                toke.val = SkoarTokeInspector.beat_long(s, s.size);
            },

            "Toke_Quavers" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = true;
                "quavers: ".post;
                s.postln;
                // size -1 for the / (we just count the o's)
                toke.val = SkoarTokeInspector.beat_short(s, s.size - 1);
                toke.val.postln;
            },

            // unrests
            "Toke_Quarters" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = false;
                toke.val = SkoarTokeInspector.beat_long(s, s.size);
            },

            "Toke_Eighths" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = false;
                toke.val = SkoarTokeInspector.beat_short(s, s.size);
            }

        ];        
        ^dict;
    }

    *beat_short {
        | s, n |
        var is_dotted = s.endsWith(".");
        var x = 0.0;

        is_dotted.postln;

        if (is_dotted == true) {
            n = n - 1;
        };

        x = 2 ** (-1 * n);

        if (is_dotted == true) {
            x = x * 1.5;
        };
        x.postln;
        ^x;
    }

    *beat_long {
        | s, n |
        var is_dotted = s.endsWith(".");
        var x = 0.0;

        if (is_dotted == true) {
            n = n - 1;
        };

        x = 2 ** (n - 1);

        if (is_dotted == true) {
            x = x * 1.5;
        };
        x.postln;
        ^x;
    }

}