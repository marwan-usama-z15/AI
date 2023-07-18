% our state represintation will be : replacing the bomb with number 3 , 2 for vertical domino , 1 for Horizontel dominothis , we will represnt it as a list of lists as the [] equals the current state [[],[],...]
replace(I, L, E, D) :-
  nth1(I, L, T, R),
  T=0,
  nth1(I, K, E, R),
  NI is I+1,
  nth1(NI,K,Y,S),
  Y=0,
  nth1(NI,D,E,S).
ree(I,L,R):-replace(I,L,1,R).

replace2(M,I, L, E, D) :-
  nth1(I, L, _, R),
  nth1(I, K, E, R),
  NI is I+M,
  nth1(NI,K,_,S),
  nth1(NI,D,E,S).


ree2(M,I,L,R):-replace2(M,I,L,2,R).

replace3(I,NI, L, E, D) :-
  nth1(I, L, _, R),
  
  nth1(I, K, E, R),

  nth1(NI,K,_,S),

  nth1(NI,D,E,S).

indexOf([Element|_], Element, 1).
indexOf([_|Tail], Element, Index):-
  indexOf(Tail, Element, Index1),
  
  Index is Index1+1.
  
indexOf2([Element|_], Element, 1):-!.
indexOf2([_|Tail], Element, Index):-
  Index1 is Index-1,
  indexOf2(Tail, Element, Index1).
  
 
getAllValidChildren(Node, Open, Closed, Children):-
findall(Next, getNextState(Node, Open, Closed, Next), Children).

getNextState([State,_], Open, Closed, [Next,State]):-
rw(State, Next),
not(member([Next,_], Open)),
not(member([Next,_], Closed)),
isOkay(Next).

  
move(L,Open,Closed,M,R):-
   indexOf(L,0,X),not(0 is X mod M) ,ree(X,L,R),not(member(R, Open)),
not(member(R, Closed)).


move(L,Open,Closed,M,R):- indexOf(L,0,X),X2 is X +M, indexOf2(L,E,X2), E = 0, ree2(M,X,L,R),not(member(R, Open)),
not(member(R, Closed)).



  
seearch(N,L,Open,Closed,Rr):-
	findall(R, move(L,Open,Closed,N,R),Rr).
ismember(X,L):-seearch(L,R),member(X,R).


getState([CurrentNode|Rest], CurrentNode, Rest).
addChildren(Children, Open, NewOpen):-
    Open \= [[]], !,
	append(Open ,Children, NewOpen).
	
addChildren(Children, _, Children):-!.


first(N,Open, _, CurrentState):-
getState(Open, CurrentState, _), % Step 1
fun2(N,CurrentState), write("this matrix where 3 for bomb, 2 for vertical domino , 1 for Horizontel domino") .



first(N,Open,Closed,L):-
     getState(Open,Curr,Res) ,
	 seearch(N,Curr,Res,Closed,RR),
	 addChildren(RR, Res, NewOpen),
	 append(Closed, [Curr], Nc),
	 first(N,NewOpen, Nc,L).


addd(M,N,P1,P11,P2,P22,L):- Z is M * N ,add(Z,[],Y), W is N * (P1-1) +P11,D is N * (P2-1) + P22,replace3(W,D,Y,3,L) ,write("the initial state":L),nl.

add(Z,L,L):-Z =0,! .
add(Z,L,Re):-
    Z1 is Z-1,
    append([0],L,X),
	add(Z1,X,Re)  .


fun(N,L):-indexOf(L,0,X),not(0 is X mod N), X2 is X+1, indexOf2(L,E,X2) , E=0 ,!.

fun(N,L):-indexOf(L,0,X), X2 is X+N, indexOf2(L,E,X2) , E=0 ,!.


fun2(N,L):-not(fun(N,L)).



bsearch(M,N,P1,P11,P2,P22,L):-addd(M,N,P1,P11,P2,P22,U),first(N,[U],[],L).



 