package practica3clasesjava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Automata {

    //Atributos clase Automata
    String _textoIma;
    int _edoAct;

    //Metodo SigCar que obtiene el siguiente caracter a comparar
    char SigCar(int[] i) {
        //Si ya no hay caracteres retorna §
        if (i[0] == _textoIma.length()) {
            i[0]++;
            return '§';
        } else {
            //Retorna el sig caracter
            return _textoIma.charAt(i[0]++);
        }
    }

    //Metodo Reconoces que efectua las comparaciones del lexema
    public boolean Reconoces(String texto, int iniToken, int[] i, int noAuto) {

        _textoIma = texto; //Obtien el texto enviado
        //Comprueba con que caso se compara el texto
        switch (noAuto) {
            //--------------  Automata  ReconoceDelim--------------
            case 0:
                return ReconoceDelim(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceID--------------
            case 1:
                return ReconoceID(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceOpRelacional--------------
            case 2:
                return ReconoceOpRelacional(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceOpAsig--------------
            case 3:
                return ReconoceOpAsig(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceIncDec --------------
            case 4:
                return ReconoceIncDec(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceOpLogico--------------
            case 5:
                return ReconoceOpLogico(texto, i, iniToken);
            //break;        
            //--------------  Automata  ReconoceNum--------------
            case 6:
                return ReconoceNum(texto, i, iniToken);
            // break;
            //--------------  Automata  ReconoceSep--------------
            case 7:
                return ReconoceSep(texto, i, iniToken);
            // break;
            //--------------  Automata  ReconoceCad--------------
            case 8:
                return ReconoceCad(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceCar--------------
            case 9:
                return ReconoceCar(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceComentario--------------
            case 10:
                return ReconoceComentario(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceComentarioLineas--------------
            case 11:
                return ReconoceComentarioLineas(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceOpArit--------------
            case 12:
                return ReconoceRaiz(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceTer--------------
            case 13:
                return ReconoceTer(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconocePotencia--------------
            case 14:
                return ReconocePotencia(texto, i, iniToken);
            //break;
            //--------------  Automata  ReconoceRaiz--------------
            case 15:
                return ReconoceOpArit(texto, i, iniToken);
        }
        return false;
    }

    //Metodo que reconoce los delimitadores
    private boolean ReconoceDelim(String texgt, int[] _i, int iniToken) {
        //Si hay un espacio
        //Expresión regular sugerida en la documentación para espacios
        Pattern pa = Pattern.compile("\\p{Space}+");
        Matcher ma = pa.matcher(Character.toString(texgt.charAt(iniToken)));
        String tex = "";
        //Leremos hasta que deje de haber espacios
        while (ma.matches()) {
            try {
                tex = texgt.substring(iniToken, ++_i[0]);
                ma = pa.matcher(tex);
                //Excepción por si llegamos a la limite leyendo espacios
            } catch (Exception e) {
                ma = pa.matcher(tex + "§");
            }
        }
        //Si ya no cumple y no es nula, es que llegamos al final
        if (!ma.matches() && !tex.equals("")) {
            _i[0]--;
            return true;
            //Recuperación de error léxico
        } else {
            _i[0] = iniToken;
            return false;
        }
    }

    //Metodo que reconoce los Identificadores
    private boolean ReconoceID(String texgt, int[] _i, int iniToken) {
        //Expresión regular
        Pattern pa = Pattern.compile("([A-Za-z]|_)([A-Za-z]|[0-9]|_)*");
        String tex = "";
        Matcher ma = pa.matcher(Character.toString(texgt.charAt(iniToken)));
        //Mientras coincida aumentamos la longitud de la cadena, saldremos del
        //ciclo cuando ya no coincida, o sea cuando acabe de leer todo el 
        //lexema
        while (ma.matches()) {
            try {
                tex = texgt.substring(iniToken, ++_i[0]);
                ma = pa.matcher(tex);
                //Excepción por si llegamos a la fronter
            } catch (Exception e) {
                ma = pa.matcher(tex + "§");
                //System.out.println(e.getMessage());
            }
        }
        //Hasta que ya no lea y no sea nula, es que llegamos al final
        if (!ma.matches() && !tex.equals("")) {
            _i[0]--;
            return true;
            //Recuperación de error léxico
        } else {
            _i[0] = iniToken;
            return false;
        }
    }

    //Metodo que reconoce los operadores de asignación
    private boolean ReconoceOpAsig(String texto, int[] _i, int iniToken) {
        //Expresión regular para los operadores, puede ser = o la combinación de
        //algo con igual
        Pattern pa = Pattern.compile("[=]|[\\+|\\-|\\*|\\/][=]");
        String txt;
        Matcher ma;
        //Sí su longitud es mayor a dos, intenaremos tomar todo el lexema
        //y compararlo
        if (texto.length() - _i[0] >= 2) {
            txt = texto.substring(iniToken, _i[0] + 2);
            ma = pa.matcher(txt);
            //Si la comparación es exitosa, adelante. Lo tomamos entero
            if (ma.matches()) {
                int i = _i[0] + 2;
                _i[0] = i;
                return true;
            } //Sí no es exitosa, tomamos el lexema la primera letra.
            //Creo que esto es innecesario
            else {
                txt = texto.substring(iniToken, ++_i[0]);
                ma = pa.matcher(txt);
                //Si coincide, regresamos
                if (ma.matches()) {
                    return ma.matches();
                } //Recupración de error léxico
                else {
                    _i[0] = iniToken;
                    return ma.matches();
                }
            }
        } //Si el primer if falla, es un igual así que nos venimos por aquí
        else {
            txt = texto.substring(iniToken, ++_i[0]);
            ma = pa.matcher(txt);
            if (ma.matches()) {
                return ma.matches();
            } //Recuperación de error léxico
            else {
                _i[0] = iniToken;
                return ma.matches();
            }

        }

    }

    //Metodo que reconoce el incremento y decremento
    private boolean ReconoceIncDec(String texto, int[] _i, int iniToken) {
        //Expresion regular
        Pattern pa = Pattern.compile("[+][+]");
        String txt;

        //Si la longitud es de dos, si podemos tener uno de estos tokens
        //si no, ya no
        if (texto.length() - _i[0] >= 2) {
            txt = texto.substring(iniToken, _i[0] + 2);
        } else {
            return false;
        }
        //Creación de la expresión regular, comparamos primero con ++
        Matcher ma = pa.matcher(txt);
        boolean m = ma.matches();
        //Brincamos de dos en dos para capturar todo el lexema
        if (m) {
            int i = _i[0] + 2;
            _i[0] = i;
            //Si falla, intentaremos compararlo con --
        } else {
            pa = Pattern.compile("[-][-]");
            ma = pa.matcher(txt);
            m = ma.matches();
            //Brincamos de dos en dos para capturar todo el lexema
            if (m) {
                int i = _i[0] + 2;
                _i[0] = i;
            }
        }
        return m;
    }

    //Metodo que reconoce los operadores aritmeticos
    private boolean ReconoceOpArit(String texto, int[] _i, int iniToken) {
        String mas = "[+]";
        String men = "[-]";
        String ent = "[/]";
        String por = "[*]";
        String mod = "[%]";
        //Expresión regular para que quede más claro todo
        Pattern pa = Pattern.compile(mas + "|" + men + "|" + ent + "|" + por + "|" + mod + "|");
        String tex = texto.substring(iniToken, ++_i[0]);
        Matcher ma = pa.matcher(tex);
        //Si coincide, fácil, solo es 1 caracter
        if (ma.matches()) {
            return true;
        } //Recuperación de error léxico
        else {
            _i[0] = iniToken;
            return false;
        }

    }

    //Metodo que reconoce los operadores relacionales
    private boolean ReconoceOpRelacional(String texto, int[] _i, int iniToken) {
        //Expresion regular
        Pattern pa = Pattern.compile("[<>]|[<>!=][=]");
        String tex = "";
        Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
        //Si contiene ! o = al inicio nos vamos por aquí.
        if (texto.substring(iniToken, _i[0]).contains("!") || texto.substring(iniToken, _i[0]).contains("=")) {
            //Mientras no coincida, aumentaremos i para ampliar el token.
            while (!ma.matches()) {
                try {
                    tex = texto.substring(iniToken, ++_i[0]);
                    ma = pa.matcher(tex);
                    //Atrapamos la excepción en caso de que hallamos llegado a la frontera
                } catch (Exception e) {
                    ma = pa.matcher(tex + "§");
                    break;
                }
            }
            //Sí estamos aquí, es porque ya coincidió con alguno. Regresamos verdadero
            if (ma.matches() && !tex.equals("")) {
                //_i[0]--;
                return true;
            } else {
                //o nos recuperamos del error léxico
                _i[0] = iniToken;
                return false;
            }
            //Por aquí venimos si no contiene ! o =
        } else {
            if (ma.matches()) {
                while (ma.matches()) {
                    try {
                        //Mientras coincida ampliamos, con la misma técnica conocida
                        tex = texto.substring(iniToken, ++_i[0]);
                        ma = pa.matcher(tex);
                        //Atrapamos el error en caso de que lleguemos a la frontera
                    } catch (Exception e) {
                        tex = texto.substring(iniToken, (_i[0]) - 1);
                        ma = pa.matcher(tex + "§");
                    }
                }
                //Si se cumplen estas condiciones, todo bien. Se reconoce.
                if (!ma.matches() && !tex.equals("")) {
                    _i[0]--;
                    return true;
                } else {
                    _i[0] = iniToken;
                    return false;
                }
            } //Recuperación de error léxico
            else {
                _i[0] = iniToken;
                return ma.matches();

            }
        }

    }

    //Metodo que reconoce los operadores logicos
    private boolean ReconoceOpLogico(String texto, int[] _i, int iniToken) {
        //Expresion regular
        Pattern pa = Pattern.compile("[\\&][\\&]|[\\|][\\|]|[!]|[?]");
        String tex = "";
        Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
        //Si no coincide, avanzaremos mientras no coincida. ¿Por qué?
        //Para tomar más caracteres del lexema y compararlos.
        if (!ma.matches()) {
            while (!ma.matches()) {
                try {
                    tex = texto.substring(iniToken, ++_i[0]);
                    ma = pa.matcher(tex);
                    //Atrapamos la excepción en caso de que lleguemos a la empresa
                } catch (Exception e) {
                    break;
                }
            }
            //Si coincide, atrapamos todo el lexema
            if (ma.matches() && !tex.equals("")) {
                //_i[0]--;
                return true;
                //Recuperación de error léxico
            } else {
                _i[0] = iniToken;
                return false;
            }
            //Segundo camino, solamente por seguridad
        } else {
            try {

                return ma.matches();
            } catch (Exception e) {
                return ma.matches();
            }
        }

    }

    //Metodo que reconoce los nuemros
    private boolean ReconoceNum(String texto, int[] _i, int iniToken) {
        //Expresion regular
        Pattern pa = Pattern.compile("[\\+|\\-]?[0-9]+");
        String tex = "";
        //Band va de bandera. Esta bandera almacena algunos resultados (seguir leyendo)
        boolean band = false;
        try {

            Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0] + 1));

            //Mientras coincida con el patrón, o sea 0-9+
            while (ma.matches()) {
                try {
                    //Hacemos lo mismo, leer tokens
                    tex = texto.substring(iniToken, ++_i[0]);
                    ma = pa.matcher(tex);
                    //En caso de que este en la frontera añadimos el caracter raro
                } catch (Exception e) {
                    tex = texto.substring(iniToken, _i[0] - 1);
                    ma = pa.matcher(tex + "§");
                }
            }
            //Si no coincide ya, y el texto no es igual a nulo, npuede ser que
            //haya terminado de reconcoer, o también que haya chocado con un 
            //punto o una E en alguna parte.
            if (!ma.matches() && !tex.equals("")) {
                //Si posee un punto al final, llamamos al ReconoceReal1
                if (tex.charAt(tex.length() - 1) == '.') {
                    band = ReconoceReal1(texto, _i, iniToken);
                } //La bandera se activa si no posee un punto al inicio. ¿Por qué?
                //Porque puede darse el caso de que sea exponencial.
                else {
                    _i[0]--;
                    //Si no tiene punto, se va a ir por aquí y al final va a regresar
                    //true, que viene arrastrando, para los números enteros (ej 98)
                    band = true;
                }
                //O también puede darse el caso de que sea un punto al inicio,
                //entonces se va a ReconoceReal2
            } else {
                if (texto.substring(iniToken, _i[0]).equals(".") || texto.charAt(1) == '.') {
                    //Si empieza con un punto, reconoce a real 3. Aquí almacemaos
                    //el reconocimiento en bandera, que regresaremos después.
                    _i[0]--;
                    band = ReconoceReal3(texto, _i, iniToken);
                } else {
                    _i[0] = iniToken;
                    band = false;
                }
            }
            //Se brinca hasta aquí si la bandera se activa. Esta bandera nos informa
            //de que intentaremos reconocer exponencial.
            if (band) {
                try {
                    char c = texto.charAt(_i[0]);
                    //Lee si al final hay una f, en cuyo caso se almacenaran como float
                    //y no como doubles, aunque todos pertenecen a num
                    if ((texto.charAt(_i[0]) == 'F') || (texto.charAt(_i[0]) == 'f')) {
                        _i[0]++;
                        return true;
                    }
                    ReconoceExponencial(texto, _i, iniToken);

                } catch (Exception e) {

                }
            }
            else{
                band = ReconoceNumeroUnaCifra(texto, _i, iniToken);
            }
        } catch (Exception e) {
        }
        //Aquí regresa la bandera con el resultado de ReconoceReal1 (que incluye
        //una llamada a ReconoceReal2) o la llamada a ReconoceReal3.
        return band;
    }
    
    private boolean ReconoceNumeroUnaCifra(String texto, int[] _i, int iniToken){
        Pattern pa = Pattern.compile("[0-9]+");
        String tex = "";
        try {

            Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));

            //Mientras coincida con el patrón, o sea 0-9+
            while (ma.matches()) {
                try {
                    //Hacemos lo mismo, leer tokens
                    tex = texto.substring(iniToken, ++_i[0]);
                    ma = pa.matcher(tex);
                    //En caso de que este en la frontera añadimos el caracter raro
                } catch (Exception e) {
                    tex = texto.substring(iniToken, _i[0] - 1);
                    ma = pa.matcher(tex + "§");
                }
            }
            if (!ma.matches() && !tex.equals("")) {
                //Si posee un punto al final, llamamos al ReconoceReal1
                    _i[0]--;
                    //Si no tiene punto, se va a ir por aquí y al final va a regresar
                    //true, que viene arrastrando, para los números enteros (ej 98)
                    return true;
            }
        }
        catch(Exception e){
            
        }
        return false;
    }

    private boolean ReconoceReal1(String texto, int[] _i, int iniToken) {
        //Aquí ya llega nuestra i alterada, o sea ya tenemos una parte del
        //lexema
        //Expresión regular para los que terminan con un .
        Pattern pa = Pattern.compile("[\\+|\\-]?[0-9]+[.]");
        String tex = "";
        Matcher ma = pa.matcher(texto.substring(iniToken, _i[0]));
        //boolean b;
        if (ma.matches()) {
            //Sí coindice con un punto, puede que sea un real2 (Ej 9.9)
            //Para eso llamamos a real2
            ReconoceReal2(texto, _i, iniToken);
        }
        return ma.matches();
    }

    private boolean ReconoceReal2(String texto, int[] _i, int iniToken) {
        //Aquí ya llega nuestra i alterada, o sea ya tenemos una parte del
        //lexema
        //Expresión regular para los que terminan con un .
        Pattern pa = Pattern.compile("[\\+|\\-]?[0-9]+[.][0-9]+");
        String tex = "";
        Matcher ma = null;
        try {
            //Intenta tomar la expresión hasta uno mas y compararla
            ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
            tex = texto.substring(iniToken, _i[0]);
        } catch (Exception e) {
            --_i[0];
            return false;
        }
        //Mientras vaya coincidiciento, aumentamos el token. Aquí ya viene
        //un caracter después del punto (.) ya que pasamos la primera prueba.
        while (ma.matches()) {
            try {
                //Esto es más bien para leer números después del punto.
                tex = texto.substring(iniToken, ++_i[0]);
                ma = pa.matcher(tex);
            } catch (Exception e) {
                tex = texto.substring(iniToken, (_i[0]) - 1);
                ma = pa.matcher(tex + "§");
            }
        }
        //Sí ya no coincide, todo bien.
        if (!ma.matches() && !tex.equals("")) {
            _i[0]--;
            return true;
        } else {
            //Recuperación de error léxico
            _i[0] = iniToken;
            return false;
        }
    }

    private boolean ReconoceReal3(String texto, int[] _i, int iniToken) {
        //Aquí ya llega nuestra i alteradas.
        Pattern pa = Pattern.compile("[\\+|\\-]?[.]|[\\+|\\-]?[.][0-9]+");
        String tex = "";
        String x = texto.substring(iniToken, ++_i[0] + 1);
        Matcher ma = null;
        try {
            //Intenta tomar la expresión hasta uno mas y compararla
            ma = pa.matcher(texto.substring(iniToken, ++_i[0] + 1));
        } catch (Exception e) {
            //Si falla, no olvidemos que llegamos aquí desde num, por lo que
            //ya tenemos todo capturado
            int i = _i[0] - 2;
            _i[0] = i;
            return false;
        }
        //Mientras vaya coincidiciento, aumentamos el token. Aquí ya viene
        //un caracter después del punto (.) ya que pasamos la primera prueba.
        while (ma.matches()) {
            try {
                //Esto es más bien para leer números después del punto.
                tex = texto.substring(iniToken, ++_i[0]);
                ma = pa.matcher(tex);
            } catch (Exception e) {
                ma = pa.matcher(tex + "§");
            }
        }
        //Sí ya no coincide, todo bien.
        if (!ma.matches() && !tex.equals("")) {
            _i[0]--;
            return true;
            //Recuperación de error léxico
        } else {
            _i[0] = iniToken;
            return false;
        }
    }

    //Este método es solamente para alterar la i y tomar el lexema completo.
    //no olvidemos que venimos de ReconoceNum, así que ya tenemos capturado
    //hasta la e
    private void ReconoceExponencial(String texto, int[] _i, int iniToken) {
        //Distintas maneras de representar, con signo o sin signo
        String e = "[e][0-9]+";
        String E = "[E][0-9]+";
        String es = "[e][+|-][0-9]+";
        String Es = "[E][+|-][0-9]+";
        //No olvidemos que aquí llega nuestra i alterada, o sea que leeremos
        //de la E en adelante
        Pattern pa = Pattern.compile(e + "|" + E + "|" + es + "|" + Es + "|[e][+|-]" + "|[E][+|-]");
        Matcher ma;
        try {
            //Sí hay una e, intentamos agarrar más allá de la e
            if (texto.charAt(_i[0]) == 'e' || texto.charAt(_i[0]) == 'E') {
                //Si hay una E aquí, tomemos el siguiente caracter para poder
                //continuar avanzando
                int i = _i[0];
                ++_i[0];

                ma = pa.matcher(texto.substring(i, ++_i[0]));
                //String texaux = texto.substring(i,_i[0]);
                String tex = "";
                //Técnica de aumentar los caracteres del token
                while (ma.matches()) {
                    try {
                        tex = texto.substring(i, ++_i[0]);
                        ma = pa.matcher(tex);
                        //Esto se ejecuta si llegamos a la frontera.
                    } catch (Exception exc) {
                        tex = texto.substring(i, (_i[0]) - 1);
                        ma = pa.matcher(tex + "§");
                    }
                }
                boolean c = ma.matches();
                if (!ma.matches() && !tex.equals("")) {
                    --_i[0];
                } else {
                    _i[0] = iniToken;
                }
            } else {
            }
            //Si nos brinca una excepción, nos regresamos a antes de la E
        } catch (Exception ex) {
            int x = _i[0] - 2;
            _i[0] = x;
        }
    }

    //Metodo que reconoce separadores
    private boolean ReconoceSep(String texto, int[] _i, int iniToken) {
        
        Pattern pa = Pattern.compile("[(]|[)]|[{]|[}]|[\\[]|[\\]]|[.]|[,]|[:]");
        String x = Character.toString(texto.charAt(iniToken));
        Matcher ma = pa.matcher(Character.toString(texto.charAt(iniToken)));
        if (ma.matches()) {
            _i[0]++;
            return true;
        } else {
            return false;
        }
    }

    //Metodo que reconoce cadenas
    private boolean ReconoceCad(String texto, int[] _i, int iniToken) {
        //Reconcer cualquier número de caracteres (1 o más) que sean parte
        //del ASCII y estén entre comillas.
        Pattern pa = Pattern.compile("[\"][\\p{ASCII}]+[\"]");
        Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
        //Mientras no coincida.
        //¿Porqué esto? Porque al inicio nunca coincidirá, ya que no tendremos
        //las comillas de cierre hasta que lleguemos al final de la cadena.
        while (!ma.matches()) {
            //Intentamos obtener todos los lexemas y vemos si comparamos
            try {
                ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
            } catch (Exception e) {
                //Excepción por si se nos acaba el espacio
                ma = pa.matcher(texto.substring(iniToken, --_i[0]));
                break;
            }
        }
        //Si coincide, es un caracter
        if (ma.matches()) {
            return true;
        } else {
            //Recuperación de erro, siguiente automata
            _i[0] = iniToken;
            return false;
        }

    }

    //Metodo que reconoce caracteres
    private boolean ReconoceCar(String texto, int[] _i, int iniToken) {
        //Cualquier ASCII entre apostrofes. Esto incuye hexadecimal y octal.
        Pattern pa = Pattern.compile("[\'][\\p{ASCII}]+[\']");
        //Si tiene un backslach al inicio
        if (texto.charAt(iniToken) == '\'') {
            Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
            String tex;
            //Mientras no coincida, leemos el siguiente caracter
            while (!ma.matches()) {
                try {
                    tex = texto.substring(iniToken, ++_i[0]);
                    ma = pa.matcher(tex);
                    //Si es una 'a' coincidirá luego luego. Si no, irá tomando de 1 por 1
                    //hasta que se complete el hexadecimal o el octal
                } catch (Exception e) {
                    //Si entramos aquí es porque nos quedamos sin espacio.
                    //!Salgamos de aquí!
                    ma = pa.matcher(texto.substring(iniToken, --_i[0]));
                    break;
                }
            }
            //Regresa si coincide. Si no entramos en la excepción, todo ha ido bien.
            //True si no entramos en la excepción, falso si entramos.
            return ma.matches();
        }

        return false;

    }

    //Metodo que reconoce terminadores de instruccion
    private boolean ReconoceTer(String texto, int[] _i, int iniToken) {
        //Expresión regular para reconocer puntos y comas
        Pattern pa = Pattern.compile("[;]");
        Matcher ma = pa.matcher(Character.toString(texto.charAt(iniToken)));
        if (ma.matches()) {
            _i[0]++;
            return true;
        } else {
            return false;
        }
    }

    //Metodo que reconoce comentarios de varias lineas
    private boolean ReconoceComentarioLineas(String texto, int[] _i, int iniToken) {
        //Reconcer cualquier número de caracteres (1 o más) que sean parte
        //del ASCII y estén entre comillas.
        Pattern pa = Pattern.compile("[/][*][\\p{ASCII}]+[*][/][\n]");
        Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
        //Mientras no coincida.
        //¿Porqué esto? Porque al inicio nunca coincidirá, ya que no tendremos
        //las comillas de cierre hasta que lleguemos al final de la cadena.
        while (!ma.matches()) {
            //Intentamos obtener todos los lexemas y vemos si comparamos
            try {
                ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
            } catch (Exception e) {
                //Excepción por si se nos acaba el espacio
                ma = pa.matcher(texto.substring(iniToken, --_i[0]));
                break;
            }
        }
        //Si coincide, es un caracter
        if (ma.matches()) {
            return true;
        } else {
            //Recuperación de erro, siguiente automata
            _i[0] = iniToken;
            return false;
        }
    }

    //Metodo que reconoce comentarios de una linea
    private boolean ReconoceComentario(String texto, int[] _i, int iniToken) {
        //Reconcer cualquier número de caracteres (1 o más) que sean parte
        //del ASCII y estén entre comillas.
        Pattern pa = Pattern.compile("[/][/][\\p{ASCII}]+[\n]");
        Matcher ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
        //Mientras no coincida.
        //¿Porqué esto? Porque al inicio nunca coincidirá, ya que no tendremos
        //las comillas de cierre hasta que lleguemos al final de la cadena.
        while (!ma.matches()) {
            //Intentamos obtener todos los lexemas y vemos si comparamos
            try {
                ma = pa.matcher(texto.substring(iniToken, ++_i[0]));
            } catch (Exception e) {
                //Excepción por si se nos acaba el espacio
                ma = pa.matcher(texto.substring(iniToken, --_i[0]));
                break;
            }
        }
        //Si coincide, es un caracter
        if (ma.matches()) {
            return true;
        } else {
            //Recuperación de erro, siguiente automata
            _i[0] = iniToken;
            return false;
        }
    }

    //Metodo que reconoce potencia
    private boolean ReconocePotencia(String texto, int[] _i, int iniToken) {
        //Expresion regular
        Pattern pa = Pattern.compile("[*][*]");
        String txt;

        //Si la longitud es de dos, si podemos tener uno de estos tokens
        //si no, ya no
        if (texto.length() - _i[0] >= 2) {
            txt = texto.substring(iniToken, _i[0] + 2);
        } else {
            return false;
        }
        //Creación de la expresión regular, comparamos primero con 
        Matcher ma = pa.matcher(txt);
        boolean m = ma.matches();
        //Brincamos de dos en dos para capturar todo el lexema
        if (m) {
            int i = _i[0] + 2;
            _i[0] = i;
        }
        return m;
    }

    //Metodo que reconoce raiz
    private boolean ReconoceRaiz(String texto, int[] _i, int iniToken) {
        //Expresion regular raiz, utilizamos este simbolo ya que es el mas cercano
        //al verdadero
        Pattern pa = Pattern.compile("[¬]");
        //Comprobar si compara
        Matcher ma = pa.matcher(Character.toString(texto.charAt(iniToken)));
        if (ma.matches()) {
            //Si reconoce
            _i[0]++;
            return true;
        } else {
            //No reconoce
            return false;
        }
    }
}
