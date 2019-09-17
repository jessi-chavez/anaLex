package practica3clasesjava;

public class Lexico {

    final int TOKREC = 16;
    final int MAXTOKENS = 500;
    String[] _lexemas;
    String[] _tokens;
    String _lexema;
    int _noTokens;
    int[] _i = {0};
    int _iniToken;
    Automata oAFD;

    private boolean EsId() {
        String[] palRes = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "if", "implements", "import", "int", "interface", "long",
            "native", "new", "package", "private", "protected", "public", "return", "short",
            "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "try", "void", "while", "leer", "visua"};

        for (String palRe : palRes) {
            if (_lexema.equals(palRe)) {
                return false;
            }
        }
        return true;
    }

    public int NoTokens() {
        return _noTokens;
    }

    public String[] Tokens() {
        return _tokens;
    }

    public String[] Lexemas() {
        return _lexemas;
    }

    public Lexico() // constructor por defecto
    {
        _lexemas = new String[MAXTOKENS];
        _tokens = new String[MAXTOKENS];
        oAFD = new Automata();
        _i[0] = 0;
        _iniToken = 0;
        _noTokens = 0;
    }

    public void Inicia() {
        _i[0] = 0;
        _iniToken = 0;
        _noTokens = 0;
    }

    public boolean Analiza(String texto) {
        boolean recAuto;
        int noAuto;

        while (_i[0] < texto.length()) {
            recAuto = false;
            noAuto = 0;
            for (; noAuto <= TOKREC && !recAuto;) {
                if (oAFD.Reconoces(texto, _iniToken, _i, noAuto)) {
                    recAuto = true;
                } else {
                    noAuto++;
                }
            }
            if (recAuto) {
                _lexema = texto.substring(_iniToken, _i[0]);
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
                    //--------------  Automata  OpAsig--------------
                    case 2:
                        _tokens[_noTokens] = "Potencia";
                        break;
                    //--------------  Automata  incremento--------------
                    case 3:
                        _tokens[_noTokens] = "OpAsig";
                        break;
                    //--------------  Automata  arit-------------- 
                    case 4:
                        _tokens[_noTokens] = "IncDecr";
                        break;
                    //--------------  Automata  arit-------------- 
                    case 5:
                        _tokens[_noTokens] = "Logico";
                        break;
                    //--------------  Automata  arit-------------- 
                    case 6:
                        _tokens[_noTokens] = "Numero";
                        break;

                    //--------------  Automata  num--------------
                    case 7:
                        _tokens[_noTokens] = "Separador";
                        break;
                    //--------------  Automata  sep--------------
                    case 8:
                        _tokens[_noTokens] = "Cadena";
                        break;
                    //--------------  Automata  termInst--------------
                    case 9:
                        _tokens[_noTokens] = "Caracter";
                        break;
                    case 10: {
                        _tokens[_noTokens] = "Comentario 1";
                        break;
                    }
                    case 11: {
                        _tokens[_noTokens] = "Comentario 2";
                        break;
                    }
                    //----------Automata terminador de instrucciòn----------
                    case 12:
                        _tokens[_noTokens] = "OpArit";
                        break;
                    case 13:
                        _tokens[_noTokens] = "TermInst";
                        break;

                    case 14:
                        _tokens[_noTokens] = "Relacional";
                        break;
                    case 15:
                        _tokens[_noTokens] = "Raiz";
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

    public void Anade(String tok, String lex) {
        _tokens[_noTokens] = tok;
        _lexemas[_noTokens++] = lex;
    }
}
