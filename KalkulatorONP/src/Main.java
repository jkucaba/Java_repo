//Jakub Kucaba

import java.util.ArrayList;
import java.lang.Math;
import static java.lang.Integer.parseInt;

public class Main {
    public static void kalkulatorZOnp(ArrayList<String> x){//ta metoda oblicza zapis ONP dostarczony jako string na wynik liczbowy
        double [] Stos = new double [20];
        int stosIndex = -1;
        for(int i=0; i<x.toArray().length; i++){//iterujemy pętlą przez całe wyrażenie
            //w warunku sprawdzamy czy mamy doczynienia z liczbą czy może z operatorem, jeśli z liczbą to dodajemy ją na stos
            if(!x.get(i).equals("+") && !x.get(i).equals("-") && !x.get(i).equals("*") && !x.get(i).equals("/") && !x.get(i).equals("(") && !x.get(i).equals(")") && !x.get(i).equals("^")){
                stosIndex++;
                Stos[stosIndex] = parseInt(x.get(i));
            }
            //w przypadku operatora bierzemy dwie wartości ze stosu (z góry i jedną przed górną) i wykonujemy odpowiednią operację
            else {
                double s1 = (Stos[stosIndex]);  //liczba z góry stosu
                int sI = stosIndex-1;
                double z = Stos[sI];            //2 liczba licząc od góry stosu
                if(x.get(i).equals("+")){
                    stosIndex--;                //zmniejszamy wierzchołek stosu o 1 i tam zapisujemy wynik operacji
                    Stos[stosIndex] = z+s1;
                }
                else if(x.get(i).equals("-")){
                    stosIndex--;
                    Stos[stosIndex] = z-s1;
                }
                else if(x.get(i).equals("*")){
                    stosIndex--;
                    Stos[stosIndex] = z*s1;
                }
                else if(x.get(i).equals("/")){
                    stosIndex--;
                    Stos[stosIndex] = z/s1;
                }
                else if(x.get(i).equals("^")){
                    stosIndex--;
                    Stos[stosIndex] = Math.pow(z,s1);
                }
            }
        }
        System.out.print(Stos[0]);

    }
    public static void obliczONP(String[] args, int j){     // metoda zamienia zwykły zapis matematyczny na postać ONP
        int stackIndex = -2;
        String [] Stack = new String [30];
        ArrayList<String> wynik= new ArrayList<String>();   // lista wynikowa do której będziemy kolejno dodawać wartości

        for(int i=0; i<args[j].length(); i++){     // po raz kolejny iterujemy przez wszystkie znaki w wyrażeniu
            //w warunku sprawdzmy czy mamy do czynienia z liczba
            if(args[j].charAt(i) != '+' && args[j].charAt(i) != '-' && args[j].charAt(i) != '*' && args[j].charAt(i) != '/' &&args[j].charAt(i) != '(' && args[j].charAt(i) != ')' && args[j].charAt(i) != '^'){
                if(i<args[j].length()-1){
                    int u = i+1;
                    // w pętli iterujemy przez wszytskie liczby i zestawiamy je ze sobą (chodzi o przypadek gdy mamy np. 1234)
                    while(args[j].charAt(u) != '+' && args[j].charAt(u) != '-' && args[j].charAt(u) != '*' && args[j].charAt(u) != '/' &&args[j].charAt(u) != '(' && args[j].charAt(u) != ')' && args[j].charAt(u) != '^' && u < args[j].length()-1){
                        u++;
                    }
                    if(args[j].charAt(u) != '+' && args[j].charAt(u) != '-' && args[j].charAt(u) != '*' && args[j].charAt(u) != '/' &&args[j].charAt(u) != '(' && args[j].charAt(u) != ')'){
                        u++;
                    }
                    StringBuilder str = new StringBuilder();
                    for(int c=i; c<u; c++){
                        str.append(args[j].charAt(c));
                    }
                    wynik.add(str.toString());
                    i=u-1;
                }
                else{
                    wynik.add(String.valueOf(args[j].charAt(i)));
                }

            }
            else{
                // przypadek pierwszy (czytamy pierwszy operator) - wykonuje się raz
                if (stackIndex == -2) {
                    stackIndex += 2;
                    Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                }
                // kolejno ustalamy czy pojawienie się operatora zdejmuje znajdujące się na stosie operatory
                else {
                    if(args[j].charAt(i)=='('){
                        stackIndex++;
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    // operator '+' zdejmuje operatory o takim samym lub wyższym priorytecie itd.
                    if(args[j].charAt(i)=='+'){
                        if(Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))&&stackIndex>0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        } else {
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }

                    }
                    if(args[j].charAt(i)=='-'){
                        if(Stack[stackIndex].equals("-") || Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/") || Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("-") || Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))&&stackIndex>0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                        else {
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                    }
                    if(args[j].charAt(i)=='*'){
                        if(Stack[stackIndex].equals("/") || Stack[stackIndex].equals("*") ||Stack[stackIndex].equals("^") ){
                            while((Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^")) &&stackIndex>0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                        else {
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                    }
                    if(args[j].charAt(i)=='/'){
                        if(Stack[stackIndex].equals("*")|| Stack[stackIndex].equals("/") ||Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))&&stackIndex>0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                        else {
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                    }
                    if(args[j].charAt(i)=='^'){
                        if(Stack[stackIndex].equals("^")){
                            wynik.add(Stack[stackIndex]);
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                        else {
                            stackIndex++;
                            Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                        }
                    }
                    if(args[j].charAt(i)==')'){
                        while(!Stack[stackIndex].equals("(") && stackIndex > 0) {
                            wynik.add(Stack[stackIndex]);
                            stackIndex--;
                        }
                        stackIndex--;
                    }
                }
            }
        }
        //w tej pętli dodajemy operatory które zostały na stosie
        for(int i=stackIndex; i>=0; i--){
            wynik.add(Stack[i]);
        }
        //wypisanie ostatecznych wyników
        System.out.println("\nPostac normalna: " + args[j]);
        System.out.print("Postac ONP: " + wynik);
        System.out.print("\nOstatecznie mamy: " + args[j] + " = ");
        kalkulatorZOnp(wynik);

    }
    public static void main(String[] args) {
        for(int i = 0; i<args.length; i++){
            obliczONP(args, i);
        }
    }
}