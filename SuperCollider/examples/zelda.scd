// First load the synthdefs from examples/synthdefs.scd"

(
x = "
    <? Zelda Theme - inspired by piano arrangement by Shinobu Amayake ?>

    130 => )

    .alice    @default => @instrument [#####   ] => @amp
    .bob      @default => @instrument [#####   ] => @amp
    .bass    @sawpulse => @instrument [###     ] => @amp  o~~~~
    .hats        @hats => @instrument [##      ] => @amp
    .snare      @snare => @instrument [##      ] => @amp
    .kick        @kick => @instrument [###     ] => @amp

    {! bass_end<x>    !! !x ) ) ) ] ]     !}
    {! bass_climb     !! | _e ]] _a# ]] c# ]  e ]] a# ]] ~o c# ] e ) } | f ) o~ _f ]] ]] ] ) } | !}

    {! bassline_a !!
      <a#, g#, f#, c#, b, a#, c>.{: .) ]] ]] ] ) ) :}
      !bass_end<f>
    !}

    {! bassline_b !!
      <a#, g#, f#, f>.{: ) ]] ]] ] ) ) :}

      !bass_climb !bass_climb

      <b, a#, c     >.{: ) ]] ]] ] ) ) :} !bass_end<f>
    !}


    {! intro !!

      .hats  {: }}} :: 4 times :}
      .snare {: }}} :: 4 times :}
      .kick  {: }}} :: 4 times :}

      .alice | _a# ))        o/. ]]  ]] ]] ] |     ]. _g#  ]] _a# )        o/.  ]]  ]] ]] ] |
      .bob   | _d  ))        o/. ]]  ]] ]] ] | _c  ].      ]]     )        o/.  ]]  ]] ]] ] |
      .bass  |  a# ) ]] ]] ] )       ]] ]] ] |  g# )              ]] ]] ]  )        ]] ]] ] |

      .alice |     ]. _g# ]] _a# )       o/. ]] ]] ]] ] |   ]    _f ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bob   | _c# ].     ]]     )       o/. ]] ]] ]] ] |   ] o~ _a ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bass  |  f# )             ]] ]] ] )      ]] ]] ] | f )              )        )      g ]   a ] |
    !}

    {! melody_a !!

      .bass !bassline_a

      .hats  {: }}} :: 7 times :} | ] ] ] ] ] ] ] ] |
      .snare {: }}} :: 8 times :}
      .kick  {: }}} :: 8 times :}

      .alice | _a# ) _f )__          o/. _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ] _d ].     ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice |  a# ))                                  o/ a# ]  ]  g# ]]  f# ]] |
      .bob   |  c# ]. _f# ]]  ]] _g# ]] _a# ]] c ]] c# ]. ]]    ]  c  ]] _a# ]] |

      .alice | g# ].  f# ]]  f ))                      )               |
      .bob   | c# ]. _g# ]]    ]] ]] _f# ]  _g# ]. ]]  ]] _f# ]] _g# ] |

      .alice |  d# ] ]]  f ]]  f# ))                  f ] d#  ] |
      .bob   | _f# ] ]] _f ]] _f# ] ]] _g# ]] _a# ) _g# ] _f# ] |

      .alice |  c# ] ]]  d# ]]  f ))                 d# ]  c# ] |
      .bob   | _f  ] ]] _d# ]] _f ] ]] _f# ]] _g# ) _f# ] _d# ] |

      .alice |  c ] ]]  d ]]  e ))                      g )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _g ] ]] _a ]] ]] _a# ] c ] |

      .alice |  f ]     _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~  _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}


    {! melody_b !!

      .bass !bassline_b

      .hats  {: ] ] ] ] ] ] ] ]] ]] :: 12 times :}
      .kick  {: )   }   )   }       :: 12 times :}
      .snare {: }   )   }   )       :: 11 times :} | ] ]]  ]]  ] ]] ]]  ]  ] |

      .alice | _a# ) _f )             o/.  _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ]  _d ].      ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice | a# )). ~o c# ) | c  ) o~ a )) f  ) |  f# )).  a# ) | a )  f )) ) |
      .bob   | c# )).    e  ) | d# )    c )) _a ) | _b  )).  c# ) | c ) _a )) ) |

      .alice |  f# )). a# ) | a )  f )) d ) |  d# )).  f# ) |  f  )  c# )) _a# ) |
      .bob   | _b  )). c# ) | c ) _a ))   ) | _f# )). _b  ) | _a# ) _f  )) _c# ) |

      .alice |  c ] ]]  d ]]  e ))                        g  )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _f ]] _g ] ]] _a ]] _a# ] c ] |

      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}

    {! fill !!
      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |
      .snare |    ]       ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .hats  |    ]       ]      ] ]       ] ]       ]  ]    |
      .kick  |    )              }         )         )       |
      .bass !bass_end<f>
    !}

    !intro
    !melody_a
    !fill
    !melody_b
    !fill

".skoar;
)

x.play;
