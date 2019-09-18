package practica3clasesjava;

public class Lexico {

    //Atributos de la clase Lexico
    final int TOKREC = 16;
    final int MAXTOKENS = 500;
    String[] _lexemas;
    String[] _tokens;
    String _lexema;
    int _noTokens;
    int[] _i = {0};
    int _iniToken;
    Automata oAFD;

    //Metodo que compara el lexema con palabras reservadas
    private boolean EsId() {
        String[] palRes = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "if", "implements", "import", "int", "interface", "long",
            "native", "new", "package", "private", "protected", "public", "return", "short",
            "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "try", "void", "while", "leer", "visua","default"};

        for (String palRe : palRes) {
            //Si es palabra reservada retorna false
            if (_lexema.equals(palRe)) {
                return false;
            }
        }
        //Si es identificador retorna true
        return true;
    }

    //Metodo que retorna el numero de tokens
    public int NoTokens() {
        return _noTokens;
    }

    //Metodo que retorna un arreglo de tokens 
    public String[] Tokens() {
        return _tokens;
    }

    //Metodo que retorna un arreglo de lexemas
    public String[] Lexemas() {
        return _lexemas;
    }

    //constructor de la clase Lexico
    public Lexico() // constructor por defecto
    {
        _lexemas = new String[MAXTOKENS];
        _tokens = new String[MAXTOKENS];
        oAFD = new Automata();
        _i[0] = 0;
        _iniToken = 0;
        _noTokens = 0;
    }

    //Metodo Inicia de la clase Lexico, inicia las variables
    public void Inicia() {
        _i[0] = 0;
        _iniToken = 0;
        _noTokens = 0;
    }

    //Metodo Analiza
    public boolean Analiza(String texto) {
        boolean recAuto; //reconoce el automata
        int noAuto; //numero del automata

        //mientras el valor de _i[0] es menor que la longitud del texto
        while (_i[0] < texto.length()) {
            recAuto = false; //poner el recAuto en false y el noAuto en 0
            noAuto = 0;
            //En este for se recorrera el automata y se convocara al metodo Reconoces de la clase Automata
            //Para tratar re reconocer el lexema
            for (; noAuto <= TOKREC && !recAuto;) {
                if (oAFD.Reconoces(texto, _iniToken, _i, noAuto)) {
                    //Si se cumple el reconocimiento retornar true
                    recAuto = true;
                } else {
                    //Si no se reconoce incrementar noAuto para intentar con otro automata
                    noAuto++;
                }
            }
            //Si se reconocio el lexema
            if (recAuto) {
                //Se obtiene el lexema
                _lexema = texto.substring(_iniToken, _i[0]);
                //Se ubica que caso fue de reconocimiento
                switch (noAuto) {
                    //--------------  Automata  delim--------------
                    case 0: //_tokens[_noTokens] = "delim";
                        break;
                    //--------------  Automata  id--------------
                    case 1:
                        if (EsId()) {
                            _tokens[_noTokens] = "id";
                        } else {
                            _tokens[_noTokens] = "palRes";
                        }
                        break;
                    //--------------  Automata  OpRel--------------
                    case 2:
                        _tokens[_noTokens] = "OpRel";
                        break;
                    //--------------  Automata  OpAsig--------------
                    case 3:
                        _tokens[_noTokens] = "OpAsig";
                        break;
                    //--------------  Automata  IncDecr-------------- 
                    case 4:
                        _tokens[_noTokens] = "IncDecr";
                        break;
                    //--------------  Automata  Logico-------------- 
                    case 5:
                        _tokens[_noTokens] = "Logico";
                        break;
                    //--------------  Automata  Numero-------------- 
                    case 6:
                        _tokens[_noTokens] = "Numero";
                        break;

                    //--------------  Automata  Separador--------------
                    case 7:
                        _tokens[_noTokens] = "Separador";
                        break;
                    //--------------  Automata  Cadena--------------
                    case 8:
                        _tokens[_noTokens] = "Cadena";
                        break;
                    //--------------  Automata  Caracter--------------
                    case 9:
                        _tokens[_noTokens] = "Caracter";
                        break;
                    //--------------  Automata  Comentario 1--------------
                    case 10: {
                        _tokens[_noTokens] = "Comentario 1";
                        break;
                    }
                    //--------------  Automata  Comentario 2--------------
                    case 11: {
                        _tokens[_noTokens] = "Comentario 2";
                        break;
                    }
                    //----------Automata OpArit----------
                    case 12:
                        _tokens[_noTokens] = "Raiz";
                        break;
                    //--------------  Automata  TermInst--------------
                    case 13:
                        _tokens[_noTokens] = "TermInst";
                        break;
                    //--------------  Automata  Potencia--------------
                    case 14:
                        _tokens[_noTokens] = "Potencia";
                        break;
                    //--------------  Automata  Raiz--------------
                    case 15:
                        _tokens[_noTokens] = "OpArit";
                        break;
                }
                if (noAuto != 0) {
                    _lexemas[_noTokens++] = _lexema;
                }
            } else {
                return false; //La parte de recuperación del error léxico se reemplaza por
            }       //este return false para indicar que existe un error léxico
            _iniToken = _i[0];
        }
        return true; //El análisis léxico ha sido exitoso cuando acaba el while 
    }
}
