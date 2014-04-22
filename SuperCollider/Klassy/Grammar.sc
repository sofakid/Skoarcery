/*
Tokes
-----

Toke_NegQBeat
Toke_NegEBeat
Toke_QBeat
Toke_EBeat
Toke_Slide
Toke_Bar
Toke_GotoDst
Toke_Goto
Toke_NoteLetter
Toke_Sharps
Toke_Flats
Toke_NoteOctave
Toke_Symbol
Toke_Args
Toke_ListSep
Toke_On
Toke_Off
Toke_Toggle
Toke_Assign
Toke_Label


skoar
fin
bar
label_list
key_set
goto_dst
goto
note
beat
neg_beat
sharps_or_flats
tickle
phrases
phrasey


Raw grammar
-----------


fin:   Toke_Bar TokeBar
skoar: bars fin

bar:   Toke_Bar phrases

label_list: Toke_Label { Toke_ListSep label_list }
key_set:    Toke_Symbol Toke_Assign ( Toke_Args | Toke_Int | Toke_Float | Toke_Symbol )

goto_dst: { label_list } Toke_GotoDst
goto: Toke_Goto { label_list }

note: Toke_NoteLetter { Toke_NoteOctave } { sharps_or_flats }

beat: Toke_QBeat | Toke_EBeat

neg_beat: Toke_NegQBeat | Toke_NegEBeat

sharps_or_flats: Toke_Sharps | Toke_Flats

tickle: Toke_On Toke_Symbol
      | Toke_Off Toke_Symbol
      | Toke_Tog Toke_Symbol


phrases: phrasey { phrases } | bar

phrasey: Tok_Symbol | key_set | note | beat | neg_beat | tickle | goto_dst | goto





Let's play
----------

skoar: phrases fin
First: { phrases.first, Toke_Bar }
Follow: {}

phrases: phrasey phrases | phrasey | bar
First: { phrasey.first, bar.first}
Follow: { phrasey.follow, bar.follow }

phrasey: key_set | note | beat | neg_beat | tickle | goto_dst | goto

First: { Toke_Symbol | Toke_NoteLetter, Toke_Symbol | Toke_QBeat, Toke_EBeat, Toke_NegQBeat, Toke_NegEBeat |
Toke_On Toke_Symbol
      | Toke_Off Toke_Symbol
      | Toke_Tog,| Toke_Label, Toke_GotoDst | Toke_Goto }

Follow: { assignable.first }

bar: Toke_Bar phrases
First: { Toke_Bar }
Follow: { phrases.first }

label_list: Toke_Label { Toke_ListSep label_list }
First: { Toke_Label }
Follow: { label_list.first }

goto: Toke_Goto { label_list }
First: { Toke_Goto }
Follow: { label_list.first }

goto_dst:   { label_list } Toke_GotoDst
First: { label_list.first, Toke_GotoDst }
Follow: { }

note: Toke_NoteLetter { Toke_NoteOctave } { sharps_or_flats } | Toke_Symbol
First: { Toke_NoteLetter, Toke_Symbol }
Follow: { sharps_or_flats.first }

assignable: Toke_Args | Toke_Int | Toke_Float | Toke_Symbol | note
First: { Toke_Args, Toke_Int, Toke_Float, Toke_Symbol, note.first }
Follow: { ??? }

key_set:    Toke_Symbol Toke_Assign assignable
First: { Toke_Symbol }
Follow: { assignable.first }

these are all just tokens:
--------------------------

beat:       Toke_QBeat | Toke_EBeat
fin:        Toke_Bar TokeBar |
neg_beat:   Toke_NegQBeat | Toke_NegEBeat

sharps_or_flats: Toke_Sharps | Toke_Flats

tickle: Toke_On Toke_Symbol
      | Toke_Off Toke_Symbol
      | Toke_Tog Toke_Symbol







Let's play Moar
---------------

skoar: phrases fin

phrases: phrasey { phrases } | bar

phrasey: key_set | note | beat | neg_beat | tickle | goto_dst | goto

bar: Toke_Bar phrases

label_list: Toke_Label { Toke_ListSep label_list }

goto: Toke_Goto { label_list }

goto_dst:   { label_list } Toke_GotoDst

note: Toke_NoteLetter octave sharps_or_flats | Toke_Symbol

assignable: Toke_Args | Toke_Int | Toke_Float | Toke_Symbol | note

key_set: Toke_Symbol Toke_Assign assignable

beat:       Toke_QBeat | Toke_EBeat

fin:        Toke_Bar TokeBar | <e>

neg_beat:   Toke_NegQBeat | Toke_NegEBeat

octave: Toke_NoteOctave | <e>

sharps_or_flats: Toke_Sharps | Toke_Flats | <e>

tickle: Toke_On Toke_Symbol
      | Toke_Off Toke_Symbol
      | Toke_Tog Toke_Symbol




Let's play EEEN Moar
--------------------

skoar: phrases fin

phrases: phrasey phrases | phrasey | bar

phrasey: key_set | note | beat | neg_beat | tickle | goto_dst | goto

bar: Toke_Bar phrases

labels: Toke_Label Toke_ListSep labels | Toke_Label | <e>

goto: Toke_Goto labels

goto_dst: labels Toke_GotoDst

note: Toke_NoteLetter octave sharps_or_flats | Toke_Symbol

assignable: Toke_Args | Toke_Int | Toke_Float | Toke_Symbol | note

key_set: Toke_Symbol Toke_Assign assignable

beat: Toke_QBeat | Toke_EBeat

fin: Toke_Bar TokeBar | <e>

neg_beat:  Toke_NegQBeat | Toke_NegEBeat

octave: Toke_NoteOctave | <e>

sharps_or_flats: Toke_Sharps | Toke_Flats | <e>

tickle: Toke_On Toke_Symbol
      | Toke_Off Toke_Symbol
      | Toke_Tog Toke_Symbol




||: f )) ^obj.msg(




*/