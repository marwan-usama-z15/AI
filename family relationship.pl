%-------------------Question 1---------------------

is_friend(X,Y):-
    friend(X,Y),!.
is_friend(X,Y):-
    friend(Y,X),!.
%-------------------Question 2---------------------
isfriend(X,Y):-
    friend(X,Y).
isfriend(X,Y):-
    friend(Y,X).
append_list([], L2, L2).
append_list([X | L1], L2, [X | L3]) :-
    append_list(L1, L2, L3),!.


memberr(X,[Y|_]):- X=Y.
memberr(X, [_|Tail]):-
	memberr(X, Tail).


fl(X,L1,U):-
      isfriend(Y,X),\+memberr(Y,L1),append_list(L1,[Y],L),fl(X,L,U).

fl(_,D,D).


friendList(X,L):-fl(X,[],L),!.
%-------------------Question 3---------------------

countt([],Result,Result).
countt([_|Tail],Z,N):-
    Z1 is Z+1,
    countt(Tail, Z1,N).
friendListCount(X,N):-
    friendList(X,L),countt(L,0,N),!.
%-------------------Question 4---------------------


peopleYouMayKnow(X,Z):-isfriend(X,Y),isfriend(Y,Z),Z\=X,\+isfriend(Z,X).

%-------------------Question 5---------------------

pym(Name,X,N,Y):-
		friendList(Name,L),
		f_of_f(L,[],Lista),
                counter(Lista,X,N,Y).

peopleYouMayKnow(X,N,A):-
    pym(X,A,N,S),S=<0,A\=X,!.


f_of_f([],X,X).

f_of_f([H|T],C_L,R):-
	friendList(H,L),
        append_list(L,C_L,N_L),
	f_of_f(T,N_L,R).

counter([], _,Y,Y).
counter([X | T],X,Y,Z) :-
   YN is Y-1,
   counter(T, X,YN,Z).
counter([_ | T],X,Y,Z) :-
  counter(T,X,Y,Z).


%-------------------Question 6---------------------

ymk(X,Z):-isfriend(X,Y),isfriend(Y,Z),Z\=X,\+isfriend(Z,X).


peopleYouMayKnowList(X,L):-
    ymkl(X,[],L),!.
ymkl(X,Collect,L):-
    ymk(X,J),\+memberr(J,Collect),append_list(Collect,[J],Coll),ymkl(X,Coll,L).
ymkl(_,Collect,Collect).
%-------------------Question 7-*Bonus*---------------------


peopleYouMayKnow_indirect(X,W):-
isfriend(X,Y),
isfriend(Y,Z),
isfriend(Z,W),
\+isfriend(W,X)
,\+isfriend(Y,W),
    is_friend(W,D),
    \+is_friend(D,X).









