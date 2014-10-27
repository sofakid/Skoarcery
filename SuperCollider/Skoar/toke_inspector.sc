// ==============
// noad.toke_inspector
// ==============
//
// Here we pick the values out of the noad.tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = IdentityDictionary[
        
            Toke_Meter.class -> {
                | noad |
                var a = noad.toke.lexeme.split;
                noad.toke.val = [a[0].asInteger, a[1].asInteger];
            },

            Toke_Carrots.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.size;
            },
            
            Toke_Tuplet.class -> {
                | noad |
                noad.toke.val = 0;
            },

            Toke_DynPiano.class -> {
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

            Toke_DynForte.class -> {
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

            Toke_OctaveShift.class -> {
                | noad |

                var s = noad.toke.lexeme;
                var n = s.size - 1;

                if (s.beginsWith("o")) {
                    n =  n * -1;
                };

                noad.toke.val = n;

            },


            Toke_BooleanOp.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme;
            },


            
            Toke_MsgName.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.asSymbol;
            },
            
            Toke_MsgNameWithArgs.class -> {
                | noad |
                var s = noad.toke.lexeme;
                noad.toke.val = s[0..s.size-2].asSymbol;
            },
            
            Toke_Volta.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.strip("[.]").asInteger;
            },
            

            Toke_SymbolName.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.asSymbol;
            },

            Toke_Voice.class -> {
                | noad |
                var s = noad.toke.lexeme;
                var n = s.size - 1;
                noad.toke.val = s[1..n].asSymbol;
            },

            Toke_Segno.class -> {
                | noad |

            },

            Toke_Bars.class -> {
                | noad |
                noad.toke.pre_repeat = noad.toke.lexeme.beginsWith(":");
                noad.toke.post_repeat = noad.toke.lexeme.endsWith(":");
            },

            Toke_Rep.class -> {
                | noad |
                noad.toke.val = noad.toke.lexeme.size;
            },


            // ------------
            // Skoarpuscles
            // ------------
            Toke_Int.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleInt(noad.toke.lexeme.asInteger);
                noad.toke = nil;
            },

            Toke_Float.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleFloat(noad.toke.lexeme.asFloat);
                noad.toke = nil;
            },

            Toke_NamedNoat.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleNoat(noad.toke.lexeme);
                noad.toke = nil;
            },

            Toke_Choard.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleChoard(noad.toke.lexeme);
                noad.toke = nil;
            },

            Toke_String.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleSymbol(noad.toke.lexeme.asString);
                noad.toke = nil;
            },

            Toke_Symbol.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleSymbol(noad.toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            // rests
            // } }} }}}
            Toke_Crotchets.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleRest(noad.toke);
                noad.toke = nil;
            },

            // o/ oo/ ooo/
            Toke_Quavers.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleRest(noad.toke);
                noad.toke = nil;
            },

            // unrests
            Toke_Quarters.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleBeat(noad.toke);
                noad.toke = nil;
            },

            Toke_Eighths.class -> {
                | noad |
                noad.skoarpuscle = SkoarpuscleBeat(noad.toke);
                noad.toke = nil;
            }

        ];

        dict.keysDo({
            | k |
            k.dump;
        });
        ^dict;
    }

}
