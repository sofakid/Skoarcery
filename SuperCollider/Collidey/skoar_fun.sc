
// c4 makes d4 assumed,
simple = " c4 ) ) ) )  d ) ) )) "


with_bars = " 4/4 |: c4 ))))  |  c ))  e ).  g ]  |  a3 ) c ] e ] g ]] ]] :|"

// with bars:
//    -  validate that each bar contains the right number of beats
//    -  start other patterns and sync on the bars
//
// time signature, 4/4 default

"foobar".findRegexp("o*bar");
"32424 334 /**aaaaaa*/".findRegexp("/\\*\\*a*\\*/");
"foobar".findRegexp("(o*)(bar)");
"aaaabaaa".findAllRegexp("a+");
"These are several words which a re fish".findAllRegexp("..e").postln;

/*

V0.1
----

BAR_SEP =: '|'

NEG_Q_BEAT =: '('
NEG_E_BEAT =: '['

Q_BEAT =: ')'
E_BEAT =: ']'

DOT =: '.'

SLIDE =: '++'

COLON =: ':'
REST =: '_'

ON  =: '+'
OFF =: '-'
TOG =: '!'

ASSIGNMENT =: '='

SYMBOL =: '\'[a-Z][a-Z0-9_]*

ARG_START =: '<'
ARG_END   =: '>'

int =: as expected
float =: just like sc

label =: [a-Z][a-Z0-9_]*

labels =: label COMMA labels
        | label

ARGS := F_ARG_START whatever F_ARG_STOP

key_set := SYMBOL.ASSIGNMENT.( ARGS | int | float | SYMBOL )

neg_beat =: DOT?.NEG_E_BEAT*.NEG_Q_BEAT+
          | DOT?.NEG_E_BEAT+

beat =: Q_BEAT+.E_BEAT*.DOT?
      | E_BEAT+.DOT?

auto_goto =: COLON

goto_dst =: labels COLON

goto =: COLON labels

midi_octave =: int

note: note_letter.sharps_or_flats.mididoctave?

sharps_or_flats: '#'+ | 'b'+

note_leter: [a-g]

bar =: BAR_SEP.auto_goto?.phrasey*
     | phrasey*

skoar =: time_sig? bar+

tickle =: ( ON | OFF | TOG ).SYMBOL

phrasey =: SYMBOL | key_set | note | beat | neg_beat | tickle | goto_dst | goto

*/

~skoar_buf_offs = 2

~accept = { | code, re |

    ~skoar_buf_offs += n;

}

~eat_code = { | code, re |
    var n = code.length;

}

~m = "|: +\pedal c4 ) e) c) g) -\pedal |: \x ]] \detune=<0,3,5> ]] ]] ]] -\detune \x \x + 1 )) _) :1,2,0||"

// tokenize

// skoar =: time_sig? bar+


    // todo match
~m.findRegexp("^[|]", offset:~skoar_buf_offs);
(
~n = ")].";

SkoarTokenQBeat.match(~n, 0)
)


/*

/*

BAR_SEP =: '|'
NEG_Q_BEAT =: '('
NEG_E_BEAT =: '['
Q_BEAT =: ')'
E_BEAT =: ']'
DOT =: '.'
SLIDE =: '++'
COLON =: ':'
REST =: '_'
ON  =: '+'
OFF =: '-'
TOG =: '!'
ASSIGNMENT =: '='
SYMBOL =: '\'[a-Z][a-Z0-9_]*
ARG_START =: '<'
ARG_END   =: '>'
int =: as expected
float =: just like sc
label =: [a-Z][a-Z0-9_]*

labels =: label COMMA labels
        | label

ARGS := F_ARG_START whatever F_ARG_STOP

key_set := SYMBOL.ASSIGNMENT.( ARGS | int | float | SYMBOL )

neg_beat =: DOT?.NEG_E_BEAT*.NEG_Q_BEAT+
          | DOT?.NEG_E_BEAT+

beat =: Q_BEAT+.E_BEAT*.DOT?
      | E_BEAT+.DOT?

auto_goto =: COLON

goto_dst =: labels COLON

goto =: COLON labels

midi_octave =: int

note: note_letter.sharps_or_flats.mididoctave?

sharps_or_flats: '#'+ | 'b'+

note_leter: [a-g]

bar =: BAR_SEP.auto_goto?.phrasey*
     | phrasey*

skoar =: time_sig? bar+

tickle =: ( ON | OFF | TOG ).SYMBOL

phrasey =: SYMBOL | key_set | note | beat | neg_beat | tickle | goto_dst | goto

*/
