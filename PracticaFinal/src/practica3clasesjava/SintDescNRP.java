/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3clasesjava;

/**
 *
 * @author Han-S
 */
public class SintDescNRP {

    public final int NODIS = 5000;

    private final  Pila _pila;
    //La primera columna es el número de producción, la segunda es el No de Yes y los otros son tokens (posición en el arreglo, núm negativos)
    //y variables terminales (posición en el arreglo, núm positivo)
    private final String[] _vts={ "", "id", "=", ";", "cad", "car", "+", "-", "*", "/", "num", "(", ")", "leer", ",", "[", "]", "visua", "$" };
    private final String[] _vns={ "", "R", "R'", "A", "A'", "E", "E'", "T", "T'", "F", "L", "X", "X''", "X'", "O", "O''", "O'", "V", "W", "W''", "W'" };
    private int[][] _prod={{1,2,3,2,0},          // R->A R' 
                           {1,2,10,2,0},          // R->L R' 
                           {1,2,17,2,0},          // R->V R' 
                           {2,2,3,2,0},          // R'->A R' 
                           {2,2,10,2,0},          // R'->L R' 
                           {2,2,17,2,0},          // R'->V R' 
                           {2,0,0,0,0},          // R'->£
                           {3,3,-1,-2,4},          // A->id = A' 
                           {4,2,5,-3,0},          // A'->E ; 
                           {4,2,-4,-3,0},          // A'->cad ; 
                           {4,2,-5,-3,0},          // A'->car ; 
                           {5,2,7,6,0},          // E->T E' 
                           {6,3,-6,7,6},          // E'->+ T E' 
                           {6,3,-7,7,6},          // E'->- T E' 
                           {6,0,0,0,0},          // E'->£
                           {7,2,9,8,0},          // T->F T' 
                           {8,3,-8,9,8},          // T'->* F T' 
                           {8,3,-9,9,8},          // T'->/ F T' 
                           {8,0,0,0,0},          // T'->£
                           {9,1,-1,0,0},          // F->id 
                           {9,1,-10,0,0},          // F->num 
                           {9,3,-11,5,-12},          // F->( E ) 
                           {10,3,-13,11,-3},          // L->leer X ; 
                           {11,2,-1,12,0},          // X->id X'' 
                           {12,2,14,13,0},          // X''->O X' 
                           {12,1,13,0,0},          // X''->X' 
                           {13,3,-14,-1,12},          // X'->, id X'' 
                           {13,0,0,0,0},          // X'->£
                           {14,2,-15,15,0},          // O->[ O'' 
                           {15,3,-1,-16,16},          // O''->id ] O' 
                           {15,3,-10,-16,16},          // O''->num ] O' 
                           {16,2,-15,15,0},          // O'->[ O'' 
                           {16,0,0,0,0},          // O'->£
                           {17,3,-17,18,-3},          // V->visua W ; 
                           {18,2,-1,19,0},          // W->id W'' 
                           {18,2,-4,20,0},          // W->cad W' 
                           {18,2,-5,20,0},          // W->car W' 
                           {18,2,-10,20,0},          // W->num W' 
                           {19,2,14,20,0},          // W''->O W' 
                           {19,1,20,0,0},          // W''->W' 
                           {20,2,-14,18,0},          // W'->, W 
                           {20,0,0,0,0}          // W'->£
                          };
      private int[][] _m={{1,1,0},
                         {1,13,1},
                         {1,17,2},
                         {2,1,3},
                         {2,13,4},
                         {2,17,5},
                         {2,18,6},
                         {3,1,7},
                         {4,1,8},
                         {4,10,8},
                         {4,11,8},
                         {4,4,9},
                         {4,5,10},
                         {5,1,11},
                         {5,10,11},
                         {5,11,11},
                         {6,6,12},
                         {6,7,13},
                         {6,3,14},
                         {6,12,14},
                         {7,1,15},
                         {7,10,15},
                         {7,11,15},
                         {8,8,16},
                         {8,9,17},
                         {8,6,18},
                         {8,7,18},
                         {8,3,18},
                         {8,12,18},
                         {9,1,19},
                         {9,10,20},
                         {9,11,21},
                         {10,13,22},
                         {11,1,23},
                         {12,15,24},
                         {12,14,25},
                         {12,3,25},
                         {13,14,26},
                         {13,3,27},
                         {14,15,28},
                         {15,1,29},
                         {15,10,30},
                         {16,15,31},
                         {16,14,32},
                         {16,3,32},
                         {17,17,33},
                         {18,1,34},
                         {18,4,35},
                         {18,5,36},
                         {18,10,37},
                         {19,15,38},
                         {19,14,39},
                         {19,3,39},
                         {20,14,40},
                         {20,3,41}
                        };
    
    public void tienePesos() {
        for(int i=0; i<_noEnt; i++) {
            String simb = _vts[_m[i][1]];
            if(simb.equals("$")) {
                System.out.println("Tiene");
            }
        }
    }

    private final int _noVts;
    private final int _noVns;
    private final int _noProd;
    private final int _noEnt;
    private final int[] _di;
    private int _noDis;

    // Metodos 
    public SintDescNRP() // Constructor -----------------------
    {
         _pila=new Pila();
         _noVts = _vts.length;
         _noVns = _vns.length;
         _noProd = 42;
         _noEnt = 55;
         _di=new int[NODIS];
         _noDis=0;
    }  // Fin del Constructor ---------------------------------------

    public void Inicia() // Constructor -----------------------
    {
        _pila.Inicia();
        _noDis = 0;
    }

    public int[][] Prod() {
        return _prod;
    }
    public int Analiza(Lexico oAnaLex) {
        //Apuntará al símbolo gramatical del tope de la pila
        SimbGram x; /*= new SimbGram("");*/
        //Apuntará al siguiente símbolo en w$
        String a;
        //Referencia al número de producción si necesitamos buscarlo en la tabla M
        int noProd;
        //Metemos el $
        _pila.Inicia();
        _pila.Push(new SimbGram("$"));
        //Mete el símbolo de inicio
        _pila.Push(new SimbGram(_vns[1]));
        //Mete el $ hasta el final de la tabla de reconocimiento del léxico
        oAnaLex.Anade("$", "$");
        //ae=0, porque w$ es un string (arrelgo de caracteres)
        int ae = 0;
        do {
            //Símbolo gramátical en el tope de la pila, primero será A
            x = _pila.Tope();
            //Regresa el arreglo de tokens y agarramos el elemento 0
            a = oAnaLex.Tokens()[ae];
            //Método para ver si el símbolo gramátical al que apunta x es terminal o no
            //X es un objeto de la clase símbolo gramátical, x regresa un string
            if (EsTerminal(x.Elem())) {
                //Si X==a saco al tope de la pila e incremento ae
                if (x.Elem().equals(a)) {
                    _pila.Pop();
                    ae++;
                    //Error
                } else {
                    return 1;
                }
            } else {
                //Revisa si existe esa producción, pasando a 'X' y a 'a'
                if ((noProd = BusqProd(x.Elem(), a)) >= 0) {
                    //Sí existe, sacamos el tope de la pila
                    _pila.Pop();
                    //y metemos todas las yes de la anterior producción empezando desde el final
                    MeterYes(noProd);
                    //En derivación hacia la izquierda mete el no prod
                    _di[_noDis++] = noProd;
                } else {
                    return 2;
                }
            }
            //Mientras no sea igual a $
        } while (!x.Elem().equals("$"));
        return 0;
    }

    //Recibe un string y recorre el arrelgo de terminales, si lo encuentra regresa verdadero,
    //si no falso
    public boolean EsTerminal(String x) {
        for (int i = 1; i < _noVts; i++) {
            if (_vts[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    //Recibe dos strings
    public int BusqProd(String x, String a) {
        int indiceX = 0;
        //Busca el índice de x en las variables no terminales
        for (int i = 1; i < _noVns; i++) {
            if (_vns[i].equals(x)) {
                indiceX = i;
                break;
            }
        }
        ///Busca el índice de a en las variables terminales
        int indiceA = 0;
        for (int i = 1; i < _noVts; i++) {
            if (_vts[i].equals(a)) {
                indiceA = i;
                break;
            }
        }
        //Checamos si en las entradas de la tabla m existe la entrada
        for (int i = 0; i < _noEnt; i++) {
            //Revisa si existe alguna entrada en la tabla m que tenga ese vns y ese vns
            if (indiceX == _m[i][0] && indiceA == _m[i][1]) {
                //Regresa el indice de la producción
                return _m[i][2];
            }
        }
        //-1 si no existe
        return -1;
    }

    //Recibe el número de la producción
    public void MeterYes(int noProd) {
        //Obtenemos el número de yes para esa producción especifica
        int noYes = _prod[noProd][1];
        //Recorremos desde 1 hasta el número de yes
        for (int i = 1; i <= noYes; i++) {
            //Tomamos de prod el renglón y el último
            if (_prod[noProd][noYes + 2 - i] < 0) {
                //Si es menor que cero, tomamos el índice en vts, pero antes hay que hacerlo positivo
                _pila.Push(new SimbGram(_vts[-_prod[noProd][noYes + 2 - i]]));
                //Si es mayor que 0 quiere decir que es variable sintáctica
                //no hay necesidad de hacerlo positivo
            } else {
                _pila.Push(new SimbGram(_vns[_prod[noProd][noYes + 2 - i]]));
            }
        }
    }

    public String[] Vts() {
        return _vts;
    }

    public String[] Vns() {
        return _vns;

    }

    public int[][] M() {
        return _m;

    }

    public int NoDis() {
        return _noDis;
    }

    public int[] Di() {

        return _di;

    }

    public int IndiceVn(String vn) {
        for (int i = 1; i < _noVns; i++) {
            if (_vns[i].equals(vn)) {
                return i;
            }
        }
        return 0;
    }

    public int IndiceVt(String vt) {
        for (int i = 1; i < _noVts; i++) {
            if (_vts[i].equals(vt)) {
                return i;
            }
        }
        return 0;
    }

    public int NoProd() {
        return _noProd;
    }
    
    public String ProdCad(int noProd) {
        //[_prod[noProd][0]] Nos sirve para obtener el número de la variable sintactica en el arreglo _vns
        String aux=""+_vns[_prod[noProd][0]]+"->";
        int noYes = _prod[noProd][1];
        if(noYes>0) {
            for(int i=2; i<=noYes+1; i++) {
                if(_prod[noProd][i]<0) {
                    aux += " "+_vts[-_prod[noProd][i]];
                } else {
                    aux += " "+_vns[_prod[noProd][i]];
                }
            }
        } else {
            aux+=" £";
        }
        return aux;
    }
    
    public int noVts() {
        return _noVts;
    }
    
    public int noEnt() {
        return _noEnt;
    }
}
