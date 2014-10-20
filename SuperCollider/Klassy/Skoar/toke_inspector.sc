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

            "Toke_Meter" -> {
                | toke |
                var a = toke.lexeme.split;
                toke.val = [a[0].asInteger, a[1].asInteger];
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
                var s = toke.lexeme;
                toke.val = switch (s)
                    {"ppp"}     {16}
                    {"pppiano"} {16}
                    {"pp"}      {33}
                    {"ppiano"}  {33}
                    {"p"}       {49}
                    {"piano"}   {49}
                    {"mp"}      {64}
                    {"mpiano"}  {64};

            },

            "Toke_DynForte" -> {
                | toke |
                var s = toke.lexeme;
                toke.val = switch(s)
                    {"mf"}      {80}
                    {"mforte"}  {80}
                    {"forte"}   {96}
                    {"ff"}      {112}
                    {"fforte"}  {112}
                    {"fff"}     {127}
                    {"ffforte"} {127};
            },

            "Toke_OctaveShift" -> {
                | toke |

                var s = toke.lexeme;
                var n = s.size - 1;

                if (s.beginsWith("o")) {
                    n =  n * -1;
                };

                toke.val = n;

            },

            "Toke_NamedNoat" -> {
                | toke |

                var noat_regex = "^(_?)([a-g])";
                var sharps_regex = "[a-g](#*|b*)$";
                var s = toke.lexeme;
                var r = s.findRegexp(noat_regex);
                var x = -1;

                toke.low = r[1][1] != "";
                toke.val = r[2][1];

                r = s.findRegexp(sharps_regex);
                s = r[1][1];

                if (s.beginsWith("#")) {
                    x = 1;
                };

                toke.sharps = s.size * x;

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

            "Toke_BlockName" -> {
                | toke |
                toke.val = toke.lexeme.asSymbol;
            },

            "Toke_BlockRef" -> {
                | toke |
                toke.val = toke.lexeme[1..].asSymbol;
            },

            "Toke_Voice" -> {
                | toke |
                var s = toke.lexeme;
                var n = s.size - 1;
                toke.val = s[1..n].asSymbol;
            },

            "Toke_Segno" -> {
                | toke |

            },
            
            "Toke_String" -> {
                | toke |
                toke.val = toke.lexeme.asString;
            },
            
            "Toke_Bars" -> {
                | toke |
                toke.pre_repeat = toke.lexeme.beginsWith(":");
                toke.post_repeat = toke.lexeme.endsWith(":");
            },

            "Toke_Rep" -> {
                | toke |
                toke.val = toke.lexeme.size;
            },

            // rests
            // } }} }}}
            "Toke_Crotchets" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = true;
                toke.val = SkoarTokeInspector.beat_long(s, s.size);
            },

            // o/ oo/ ooo/
            "Toke_Quavers" -> {
                | toke |
                var s = toke.lexeme;
                toke.is_rest = true;
                // size -1 for the / (we just count the o's)
                toke.val = SkoarTokeInspector.beat_short(s, s.size - 1);
            },

            // unrests
            "Toke_Quarters" -> {
                | toke |
                var s = toke.lexeme;
                var n = s.size;

                if (s.beginsWith(".")) {
                    n = n - 1;
                    toke.is_staccato = true;
                } {
                    toke.is_staccato = false;
                };
                toke.is_rest = false;
                toke.val = SkoarTokeInspector.beat_long(s, n);
            },

            "Toke_Eighths" -> {
                | toke |
                var s = toke.lexeme;
                var n = s.size;

                if (s.beginsWith(".")) {
                    n = n - 1;
                    toke.is_staccato = true;
                } {
                    toke.is_staccato = false;
                };

                toke.is_rest = false;
                toke.val = SkoarTokeInspector.beat_short(s, n);
            }

        ];        
        ^dict;
    }

    *beat_short {
        | s, n |
        var is_dotted = s.endsWith(".");
        var x = 0.0;

        if (is_dotted == true) {
            n = n - 1;
        };

        x = 2 ** (-1 * n);

        if (is_dotted == true) {
            x = x * 1.5;
        };
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
        ^x;
    }

}
