# ==========================================
# FIRST and FOLLOW sets from the dragon book
# ==========================================
from Skoarcery.langoids import Nonterminal, Production, Langoid

FIRST = None
FOLLOW = None


def init(compute=True):
    global FIRST, FOLLOW

    FIRST = DragonSet("FIRST")
    FOLLOW = DragonSet("FOLLOW")

    print("Dragon sets initialized.")

    if compute:
        compute_firsts()
        compute_follows()

    print("Dragon sets computed")


class DragonSet:

    def __init__(self, name):
        self.name = name
        self.D = dict()
        self.done_precomputations = False

    def __call__(self, *args):

        key = ""
        production = args[0]

        if not production:
            print("THE FUH? " + repr(args))
            raise AssertionError

        if isinstance(production, str):
            key = production

        if isinstance(production, Langoid):
            key = production.name

        if isinstance(production, Production):
            production = production.production

        if isinstance(production, list):
            for langoid in production:
                key += langoid.name

        #print("Key: " + key + " < " + str(production) + " < " + repr(args[0]))
        try:
            S = self.D[key]

        except KeyError:

            if self.done_precomputations and self.name == "FIRST":
                if not isinstance(production, list):
                    raise AssertionError

                S = FIRST_SEQ(production)
            else:
                S = set()

            self.D[key] = S

        return S

    def __len__(self):
        i = 0

        for S in self.D.values():
            i += len(S)

        return i

    def add_element(self, key, element):

        #print("add-Elemetn: " + repr(key) + str(key.__class__))

        try:
            S = self.D[key.name]
        except KeyError:
            S = set()

        S.add(element)
        self.D[key.name] = S

    def add_all_from(self, key, S):

        try:
            X = self.D[key.name]
        except KeyError:
            X = set()

        X.update(S)
        self.D[key.name] = X

    def add_all_except_e_from(self, key, S):
        from Skoarcery.tokens import Empty

        try:
            X = self.D[key.name]
        except KeyError:
            X = set()

        X.update(S.intersection({Empty}))
        self.D[key.name] = X

    def __str__(self):
        s = ""

        for k, v in self.D.items():
            s += self.name + "(" + str(k) + "): " + str(v) + "\n"

        return s


def compute_firsts():

    from Skoarcery.tokens import Empty, tokens as T
    from Skoarcery.nonterminals import nonterminals as N

    global FIRST

    # do terminals first
    for X in T.values():
        FIRST(X).add(X)

    last = 0
    first_len = len(FIRST)
    while first_len > last:

        last = first_len

        for X in N.values():
            if X.derives_empty:
                FIRST(X).add(Empty)

            for R in X.production_rules:

                i = -1
                n = len(R.production)

                # figure out FIRST(X) first
                for Yi in R.production:
                    i += 1

                    Yi_to_end = R.production[i:]

                    if len(Yi_to_end) > 0:
                        S = FIRST(Yi_to_end)

                        S.update(
                            everything_but_e(FIRST(Yi))
                        )

                        FIRST(X).update(S)
                        FIRST(Yi_to_end).update(S)

                    if not Yi.derives_empty:
                        break

                # if we got to the end of the loop without breaking, add Empty
                else:
                    FIRST(X).add(Empty)

                # finish off the suffixes
                for j in range(i, n):
                    Yj_to_end = R.production[j:]
                    Yj = R.production[j]

                    if len(Yj_to_end) > 0:
                        S = FIRST(Yj_to_end)

                        S.update(
                            everything_but_e(FIRST(Yj))
                        )

                        FIRST(Yj_to_end).update(S)

        first_len = len(FIRST)

    FIRST.done_precomputations = True

def everything_but_e(S):
    from Skoarcery.tokens import Empty

    return {el for el in S if el != Empty}


#noinspection PyPep8Naming
def FIRST_SEQ(list_of_langoids):
    from Skoarcery.tokens import Empty

    global FIRST

    OUT = set()

    for Yi in list_of_langoids:

        S = FIRST(Yi)

        OUT.update(everything_but_e(S))

        if Empty not in S:
            break

    # if we got to the end of the loop without breaking, add Empty
    else:
        OUT.add(Empty)

    return OUT


#noinspection PyPep8Naming
def compute_follows():
    from Skoarcery.tokens import EOF, Empty
    from Skoarcery.nonterminals import nonterminals as N, SKOAR

    global FIRST, FOLLOW

    # start symbol gets end symbol
    FOLLOW(SKOAR).add(EOF)

    # repeat until nothing can be added to any follow set
    last = 0
    follow_len = len(FOLLOW)
    while follow_len > last:

        last = follow_len

        for X in N.values():

            for R in X.production_rules:

                A = R.production

                # If there is a production [ A -> alpha B beta]:
                #     everything except <e> in FIRST(beta) is in FOLLOW(B)

                # examine each suffix (except last)
                n = len(A)

                for i in range(0, n - 1):

                    B = A[i]
                    if not isinstance(B, Nonterminal):
                        continue

                    beta = A[i+1:]

                    #print("n: " + str(n) + " i: " + str(i) + " A: " + repr(A) + " beta: " + repr(beta))

                    S = FIRST(beta)
                    FOLLOW(B).update(everything_but_e(S))

                for i in reversed(range(0, n)):

                    B = A[i]
                    if not isinstance(B, Nonterminal):
                        continue

                    # we are at the end of the list
                    if i == n - 1:
                        FOLLOW(B).update(FOLLOW(X))
                        continue

                    beta = A[i+1:]

                    S = FIRST(beta)

                    #print(": FIRST(" + repr(beta) + ") = " + repr(S))

                    if Empty in S:
                        FOLLOW(B).update(FOLLOW(X))
                    else:
                        break

        follow_len = len(FOLLOW)


