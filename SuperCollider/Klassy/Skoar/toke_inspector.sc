// ==============
// noad.toke_inspector
// ==============
//
// Here we pick the values out of the noad.tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = Dictionary[
        
            "Toke_Meter" -> {
                | noad |
                var a = noad.toke.lexeme.split;
                noad.toke.val = [a[0].asInteger, a[1].asInteger];
            },

            "Toke_Carrots" -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.size;
            },
            
            "Toke_Tuplet" -> {
                | noad |
                noad.toke.val = 0;
            },

            "Toke_DynPiano" -> {
                | noad |
                var s = noad.toke.lexeme;
                noad.toke.val = switch (s)
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
                | noad |
                var s = noad.toke.lexeme;
                noad.toke.val = switch(s)
                    {"mf"}      {80}
                    {"mforte"}  {80}
                    {"forte"}   {96}
                    {"ff"}      {112}
                    {"fforte"}  {112}
                    {"fff"}     {127}
                    {"ffforte"} {127};
            },

            "Toke_OctaveShift" -> {
                | noad |

                var s = noad.toke.lexeme;
                var n = s.size - 1;

                if (s.beginsWith("o")) {
                    n =  n * -1;
                };

                noad.toke.val = n;

            },


            "Toke_BooleanOp" -> {
                | noad |
                noad.toke.val = noad.toke.lexeme;
            },


            
            "Toke_MsgName" -> {
                | noad |
                noad.toke.val = noad.toke.lexeme;
            },
            
            "Toke_MsgNameWithArgs" -> {
                | noad |
                var x = "<";
                var s = noad.toke.lexeme;

                if (s.last == x) {
                    s = s.copyRange(0, s.size - 2);
                };

                noad.toke.val = s;
            },
            
            "Toke_Volta" -> {
                | noad |
                noad.toke.val = int(noad.toke.lexeme.strip("[.]"));
            },
            

            "Toke_SkoarpionName" -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.asSymbol;
            },

            "Toke_Voice" -> {
                | noad |
                var s = noad.toke.lexeme;
                var n = s.size - 1;
                noad.toke.val = s[1..n].asSymbol;
            },

            "Toke_Segno" -> {
                | noad |

            },

            "Toke_Bars" -> {
                | noad |
                noad.toke.pre_repeat = noad.toke.lexeme.beginsWith(":");
                noad.toke.post_repeat = noad.toke.lexeme.endsWith(":");
            },

            "Toke_Rep" -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.size;
            },


            // ------------
            // Skoarpuscles
            // ------------
            "Toke_SkoarpionRef" -> {
                | noad |
                noad.val = SkoarpuscleSkoarpionRef(noad.toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            "Toke_Int" -> {
                | noad |
                noad.val = SkoarpuscleInt(noad.toke.lexeme.asInteger);
                noad.toke = nil;
            },

            "Toke_Float" -> {
                | noad |
                noad.val = SkoarpuscleFloat(noad.toke.lexeme.asFloat);
                noad.toke = nil;
            },

            "Toke_NamedNoat" -> {
                | noad |
                "fllsslsl".postln;
                noad.val = SkoarpuscleNoat(noad.toke.lexeme);
                noad.toke = nil;
            },

            "Toke_Choard" -> {
                | noad |
                noad.val = SkoarpuscleChoard(noad.toke.lexeme);
                noad.toke = nil;
            },

            "Toke_String" -> {
                | noad |
                noad.val = SkoarpuscleSymbol(noad.toke.lexeme.asString);
                noad.toke = nil;
            },

            "Toke_Symbol" -> {
                | noad |
                noad.val = SkoarpuscleSymbol(noad.toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            // rests
            // } }} }}}
            "Toke_Crotchets" -> {
                | noad |
                noad.val = SkoarpuscleRest(noad.toke);
                noad.toke = nil;
            },

            // o/ oo/ ooo/
            "Toke_Quavers" -> {
                | noad |
                noad.val = SkoarpuscleRest(noad.toke);
                noad.toke = nil;
            },

            // unrests
            "Toke_Quarters" -> {
                | noad |
                noad.val = SkoarpuscleBeat(noad.toke);
                noad.toke = nil;
            },

            "Toke_Eighths" -> {
                | noad |
                noad.val = SkoarpuscleBeat(noad.toke);
                noad.toke = nil;
            }

        ];        
        ^dict;
    }

}
