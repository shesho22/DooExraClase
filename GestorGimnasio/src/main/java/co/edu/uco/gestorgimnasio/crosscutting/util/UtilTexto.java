package co.edu.uco.gestorgimnasio.crosscutting.util;


public class UtilTexto {
	
	public static final String VACIO = "";
	private static final String PATTERN_SOLO_LETRAS = "^[a-zA-ZáéíóúÁÉÍÓÚ ]+$";
	private static final String PATTERN_SOLO_LETRAS_DIGITOS_ESPACIOS = "^[a-zA-ZáéíóúÁÉÍÓÚ1234567890 ]+$";
	private static final String SOLO_NUMEROS="[0123456789 ]";
	
	private UtilTexto() {
		super();
	}
	
	public static final String obtenerValorDefecto(final String valor,final String valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(valor, valorDefecto);
	}
	
	public static final String obtenerValorDefecto(final String valor) {
		return obtenerValorDefecto(valor,VACIO);
	}
	
	public static final String aplicarTrim(final String valor) {
		return obtenerValorDefecto(valor).trim();
	}
	
	public static final boolean igualSinTrim(final String valorUno, final String valorDos) {
		return obtenerValorDefecto(valorDos).equals(obtenerValorDefecto(valorDos));
	}
	
	public static final boolean igualConTrim(final String valorUno, final String valorDos) {
		return aplicarTrim(valorDos).equals(aplicarTrim(valorDos));
	}
	
	public static final boolean igualSinTrimIgnoreCase(final String valorUno, final String valorDos) {
		return obtenerValorDefecto(valorDos).equalsIgnoreCase(obtenerValorDefecto(valorDos));
	}
	
	public static final boolean igualConTrimIgnoreCase(final String valorUno, final String valorDos) {
		return aplicarTrim(valorDos).equalsIgnoreCase(aplicarTrim(valorDos));
	}
	
	public static final boolean estaNulo(final String valor) {
		return 	UtilObjeto.esNulo(valor);
	}
	
	public static final boolean estaVacio(final String valor) {
		return igualConTrim(valor, VACIO);
	}
	
	public static final boolean longitudMinimaValida(final String valor, final int longitud) {
		return aplicarTrim(valor).length() >= longitud;
	}
	
	public static final boolean longitudMaximaValida(final String valor, final int longitud) {
		return aplicarTrim(valor).length() <= longitud;
	}
	
	public static final boolean longitudValida(final String valor, final int longitudMinima, final int longitudMaxima) {
		return longitudMinimaValida(valor, longitudMinima) && longitudMinimaValida(valor, longitudMaxima);
	}
	
	public static final boolean contieneSoloLetras(final String valor) {
		return aplicarTrim(valor).matches(PATTERN_SOLO_LETRAS);
	}
	
	public static final boolean contieneSoloLetrasDigitosEspacios(final String valor) {
		return aplicarTrim(valor).matches(PATTERN_SOLO_LETRAS_DIGITOS_ESPACIOS);
	}
	
	public static final boolean contieneSoloNumeros(final String valor) {
		return aplicarTrim(valor).matches(SOLO_NUMEROS);
	}
}
