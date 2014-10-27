// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = IdentityDictionary[
        
            \Toke_Meter -> {
                | noad, toke |
                var a = toke.lexeme.split;
                toke.val = [a[0].asInteger, a[1].asInteger];
            },

            \Toke_Carrots -> {
                | noad, toke |
                toke.val = toke.lexeme.size;
            },
            
            \Toke_Tuplet -> {
                | noad, toke |
                toke.val = 0;
            },

            \Toke_DynPiano -> {
                | noad, toke |
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

            \Toke_DynForte -> {
                | noad, toke |
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

            \Toke_OctaveShift -> {
                | noad, toke |

                var s = toke.lexeme;
                var n = s.size - 1;

                if (s.beginsWith("o")) {
                    n =  n * -1;
                };

                toke.val = n;

            },

            \Toke_BooleanOp -> {
                | noad, toke |
                toke.val = toke.lexeme;
            },

            \Toke_MsgName -> {
                | noad, toke |
                toke.val = toke.lexeme.asSymbol;
            },
            
            \Toke_MsgNameWithArgs -> {
                | noad, toke |
                var s = toke.lexeme;
                toke.val = s[0..s.size-2].asSymbol;
            },
            
            \Toke_Volta -> {
                | noad, toke |
                toke.val = toke.lexeme.strip("[.]").asInteger;
            },
            
            \Toke_SymbolName -> {
                | noad, toke |
                toke.val = toke.lexeme.asSymbol;
            },

            \Toke_Voice -> {
                | noad, toke |
                var s = toke.lexeme;
                var n = s.size - 1;
                toke.val = s[1..n].asSymbol;
            },

            \Toke_Segno -> {
                | noad, toke |

            },

            \Toke_Bars -> {
                | noad, toke |
                toke.pre_repeat = toke.lexeme.beginsWith(":");
                toke.post_repeat = toke.lexeme.endsWith(":");
            },

            \Toke_Rep -> {
                | noad, toke |
                toke.val = toke.lexeme.size;
            },


            // ------------
            // Skoarpuscles
            // ------------
            \Toke_Int -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleInt(toke.lexeme.asInteger);
            },

            \Toke_Float -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleFloat(toke.lexeme.asFloat);
            },

            \Toke_NamedNoat -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleNoat(toke.lexeme);
            },

            \Toke_Choard -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleChoard(toke.lexeme);
            },

            \Toke_String -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbol(toke.lexeme.asString);
            },

            \Toke_Symbol -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbol(toke.lexeme[1..].asSymbol);
            },

            // rests
            // } }} }}}
            \Toke_Crotchets -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
            },

            // o/ oo/ ooo/
            \Toke_Quavers -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
            },

            // unrests
            \Toke_Quarters -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
            },

            \Toke_Eighths -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
            }

        ];
        ^dict;
    }

}
