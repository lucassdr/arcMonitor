package br.unigranrio.arcd.resources.utils;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	static public Integer booleanToInt(Boolean bool) {
		return bool ? 1 : 0;
	}

	static public String generateCodeLink() {
		Date date = new Date();
		return Utils.md5(date.toString());
	}

	static public String brazilianMetrics(Double value) {
		return brazilianMetrics(value, 1);
	}

	static public String brazilianMetrics(Double value, Integer decimalPlaces) {
		if (value == null) {
			return "";
		} else if (value.intValue() == value) {
			decimalPlaces = 0;
		}

		String mask = String.format("%%.%df", decimalPlaces);
		String str = String.format(mask, value);
		return str.replace(".", ",");
	}

	static public String strCurrentDate() {
		return dateToString(currentDate());
	}

	static public Date currentDate() {
		return new Date();
	}

	static public String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

	static public String dateTimeToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(date);
	}

	static public String dateTimeToStringTimeZone(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdf.format(date);
	}

	static public String dateBrazilianTimeToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(date);
	}

	static public String dateToStringTimeZone(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	static public String sqlBrazilianDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdf.format(date);
	}

	static public String sqlBrazilianDateToMonthYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
		return sdf.format(date);
	}

	static public String timeToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	static public Date currentDateTime() {
		return currentDate();
	}

	static public Date sqlDateToDate(String sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public Date sqlDateToDateTime(String sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			return sdf.parse(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public Date sqlBrazilianDateToDateTime(String sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			return sdf.parse(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public Date sqlStringToDate(String sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public Date sqlDateToTime(String sqlTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			return sdf.parse(sqlTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String stringToFormatString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = "";
		if (data != null) {
			String dataEmUmFormato = sdf.format(data);
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date dataNova;
			try {
				dataNova = formato.parse(dataEmUmFormato);
				formato.applyPattern("yyyy-MM-dd");
				dataFormatada = formato.format(dataNova);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dataFormatada;
	}

	static public Properties loadProperties(String filename) {
		Properties properties = new Properties();
		String propFilename = String.format("%s.properties", filename);
		propFilename = propFilename.replace(".properties.properties", ".properties");
		try {
			Resource resource = new ClassPathResource(propFilename);
			InputStream iStream = resource.getInputStream();
			properties.load(iStream);
			iStream.close();
		} catch (Exception e) {
			return null;
		}
		return properties;
	}

	static public String md5(String value) {

		if (value == null) {
			return null;
		}

		String md5Password = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(value.getBytes());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			md5Password = bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5Password;
	}

	static public Integer calculateAge(Date birthDate) {
		Date currentDate = new Date();
		return yearsBetween(birthDate, currentDate);
	}

	static public Integer yearsBetween(Date fromDate, Date toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer age = Integer.parseInt(sdf.format(toDate)) - Integer.parseInt(sdf.format(fromDate));

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/2016");
		String strDtFrom2016 = sdfDate.format(fromDate);
		String strdDtTo2016 = sdfDate.format(toDate);

		Date dtFrom2016 = null;
		Date dtTo2016 = null;
		Integer offset = 0;
		try {
			dtFrom2016 = sdfDate.parse(strDtFrom2016);
			dtTo2016 = sdfDate.parse(strdDtTo2016);
			if (dtFrom2016.before(dtTo2016)) {
				offset = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return age - offset;
	}

	/*
	 * static public Boolean hasUserRole() { String[] roles = {"ROLE_USER",
	 * "ROLE_SUPER_ADMIN", "ROLE_SUPER_ADMIN"}; return hasRoles(roles); }
	 * 
	 * static public Boolean hasInactiveAccountRole() { String[] roles =
	 * {"ROLE_INACTIVE_ACCOUNT"}; return hasRoles(roles); }
	 * 
	 * static public Boolean hasIncompleteInfoRole() { String[] roles =
	 * {"ROLE_INCOMPLETE_INFO"}; return hasRoles(roles); }
	 */

	/**
	 * Verifica se dois valores double são iguais<br>
	 * Para fugir do erro de casas decimais em números float e double - Bug do C de
	 * ponto flutuante<br>
	 * Considera até 3 casas decimais
	 */
	static public Boolean isDoubleEquals(Double left, Double right) {
		return isDoubleEquals(left, right, 3);
	}

	/**
	 * Verifica se dois valores double são iguais<br>
	 * Para fugir do erro de casas decimais em números float e double - Bug do C de
	 * ponto flutuante
	 *
	 * @param decimalPrecisionDigits - Considerar até a quantidade de casas decimais
	 *                               informadas
	 */
	static public Boolean isDoubleEquals(Double left, Double right, Integer decimalPrecisionDigits) {
		// Para fugir do erro de casas decimais em números float e double - Bug do C de
		// ponto flutuante
		Double multipler = Math.pow(10, decimalPrecisionDigits.doubleValue());

		int multLeft = (int) (left * multipler);
		int multRight = (int) (right * multipler);

		return (multLeft == multRight);
	}

	/*
	 * public static Authentication currentAuthentication() { SecurityContext
	 * context = SecurityContextHolder.getContext(); if (context == null) { return
	 * null; }
	 * 
	 * Authentication authentication = context.getAuthentication(); if
	 * (authentication == null) { return null; } return authentication; }
	 * 
	 * static public Boolean hasRoles(String[] roles) { List<String> listRoles =
	 * Arrays.asList(roles);
	 * 
	 * Authentication authentication = currentAuthentication(); if (authentication
	 * == null) { return false; }
	 * 
	 * for (GrantedAuthority auth : authentication.getAuthorities()) { String
	 * currentRole = auth.getAuthority(); if (listRoles.contains(currentRole)) {
	 * return true; } } return false; }
	 * 
	 * static public void logoff() { SecurityContext context =
	 * SecurityContextHolder.getContext(); if (context != null) {
	 * context.setAuthentication(null); } }
	 */

	static public String runJS(String jsCommand) {
		return String.format("<script>parent.%s</script>", jsCommand);
	}

	static public Boolean isValidEmail(String email) {
		if (email.indexOf("@") <= 0) {
			return false;
		}
		return true;
	}

	public static String convertAccentChars(String htmlText) {
		Map<String, String> characters = new HashMap<String, String>();

		// Agudo
		characters.put("&aacute;", "á");
		characters.put("&eacute;", "é");
		characters.put("&iacute;", "í");
		characters.put("&oacute;", "ó");
		characters.put("&uacute;", "ú");
		characters.put("&Aacute;", "Á");
		characters.put("&Eacute;", "É");
		characters.put("&Iacute;", "Í");
		characters.put("&Oacute;", "Ó");
		characters.put("&Uacute;", "Ú");

		// Til
		characters.put("&ntilde;", "ñ");
		characters.put("&Ntilde;", "Ñ");
		characters.put("&atilde;", "ã");
		characters.put("&Atilde;", "Ã");

		// Cedilha
		characters.put("&ccedil;", "ç");
		characters.put("&Ccedil;", "Ç");

		// Circunflexo
		characters.put("&acirc;", "â");
		characters.put("&ecirc;", "ê");
		characters.put("&icirc;", "î");
		characters.put("&ocirc;", "ô");
		characters.put("&ucirc;", "û");
		characters.put("&Acirc;", "Â");
		characters.put("&Ecirc;", "Ê");
		characters.put("&Icirc;", "Î");
		characters.put("&Ocirc;", "Ô");
		characters.put("&Ucirc;", "Û");

		for (Map.Entry<String, String> character : characters.entrySet()) {
			String key = character.getKey();
			String value = character.getValue();
			htmlText.replaceAll(value, key);
		}

		return htmlText;
	}

	public static Map<String, Object> jsonResponse() {
		return jsonResponse(null);
	}

	public static Map<String, Object> jsonResponse(String message) {
		Map<String, Object> json = new HashMap<>();
		json.put("success", message == null);
		if (message != null) {
			json.put("message", message);
		}
		return json;
	}

	public static Date brazilianDateToDate(String brazilianDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(brazilianDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date brazilianMonthYearToDate(String brazilianDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
		try {
			return sdf.parse(brazilianDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlAvatar(String avatar) {
		return String.format("https://zarho-resources.s3.amazonaws.com/infohealth/profiles/%s", avatar);
	}

	public static String getJson(Object pojo) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(pojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String secureURL(String avatarImage) {

		return avatarImage.replace("http://", "https://");
	}

	public static Double stringToFloat(String strValue) {
		strValue = strValue.replace(",", ".");
		try {
			return Double.parseDouble(strValue);
		} catch (Exception e) {
			return 0.00;
		}
	}

	public static Double convertStringToNumber(String value) {
		if (value.contains(".") && value.contains(",")) {
			return Double.parseDouble(value.replace(".", "").replace(",", "."));
		} else {
			return Double.parseDouble(value.replace(",", "."));
		}
	}

	public static String removeCharactersSpecial(String value) {
		if (value != null) {
			return value.replaceAll("[^a-zA-Z0-9]+", "");
		} else {
			return null;
		}

	}

	public static Date stringToDate(String strDate) {
		return brazilianDateToDate(strDate);
	}

	public static String getPeriodoLetivoFormatado(String periodoLetivo, boolean mesComMaisDeCaractere) {
		if (periodoLetivo != null) {
			if (mesComMaisDeCaractere) {
				if (periodoLetivo.length() != 6) {
					return periodoLetivo; // Não formatado
				} else {
					return periodoLetivo.substring(0, 4) + "/" + periodoLetivo.substring(4, 6);
				}
			} else {
				if (periodoLetivo.length() != 5) {
					return periodoLetivo; // Não formatado
				} else {
					return periodoLetivo.substring(0, 4) + "/" + periodoLetivo.substring(4, 5);
				}
			}
		} else {
			return null;
		}
	}

	// public static void enviaEmail(Enterprise enterprise, String toEmail, String
	// subject, String body, List<String> mailsCC) {
	//
	// EnviarEmail ee = new EnviarEmail();
	// ee.sendMail(enterprise.getEmailApresentacao(),
	// enterprise.getAgreeReceiveEmailEveryRequest(),
	// mailsCC,
	// toEmail,
	// null,
	// subject,
	// body);
	// }
	//
	// public static void enviaEmail(Enterprise enterprise, String toEmail, String
	// toName, String subject, String body, List<String> mailsCC) {
	// EnviarEmail ee = new EnviarEmail();
	// ee.sendMail(enterprise.getEmailApresentacao(),
	// enterprise.getAgreeReceiveEmailEveryRequest(),
	// mailsCC,
	// toEmail,
	// toName,
	// subject,
	// body);
	// }

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date convertDateStringToDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
			sdf.setTimeZone(tz);
			return sdf.parse(strDate.trim());
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDateByPeriod(String period) {

		Date date = null;
		Calendar cal = Calendar.getInstance();

		switch (period) {
		case "today":
			date = new Date();
			break;
		case "yesterday":
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
			break;
		case "week":
			cal.add(Calendar.DATE, -7);
			date = cal.getTime();
			break;
		case "30-days":
			cal.add(Calendar.DATE, -30);
			date = cal.getTime();
			break;
		case "60-days":
			cal.add(Calendar.DATE, -60);
			date = cal.getTime();
			break;
		case "90-days":
			cal.add(Calendar.DATE, -90);
			date = cal.getTime();
			break;
		}

		return date;
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String getDataPorExtenso() {
		// PEGO AQUI A DATA ATUAL
		Date dataAtual = new Date();

		// CRIO AQUI UM FORMATADOR
		// PASSANDO UM ESTILO DE FORMATAÇÃO : DateFormat.FULL
		// E PASSANDO UM LOCAL DA DATA : new Locale("pt", "BR")
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));

		// FORMATO A DATA, O FORMATADOR ME RETORNA
		// A STRING DA DATA FORMATADA
		String dataExtenso = formatador.format(dataAtual);

		// MOSTRA A DATA
		System.out.println("Data com o dia da semana : " + dataExtenso);

		// AQUI É CASO VOCÊ QUEIRA TIRAR
		// O DIA DA SEMANA QUE APARECE NO
		// PRIMEIRO EXEMPLO
		// int index = dataExtenso.indexOf(",");
		// int lenght = dataExtenso.length();
		// System.out.println("Data sem o dia da semana : " +
		// dataExtenso.substring(++index, lenght));

		return dataExtenso;
	}

	public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

	public static String formatNumeroDecimais(Number numero) {
		if (numero == null)
			return "0.00";
		NumberFormat nf = DecimalFormat.getNumberInstance(new Locale("pt", "BR"));

		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

		return nf.format(numero);
	}

	public static String formatNumeroMonetario(Number numero) {
		if (numero == null)
			return "R$ 0.00";

		NumberFormat nf = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));

		return nf.format(numero);
	}

	public static Float parseNumeroFloat(String numero) {
		if (numero == null)
			return null;
		return parseNumeroFloat(numero, false);
	}

	public static Float parseNumeroFloat(String numero, boolean zeroSeVazio) {
		if (zeroSeVazio && StringUtils.isEmpty(numero)) {
			return 0f;
		}

		Number numeroParseado = parse(numero);

		return numeroParseado == null ? null : numeroParseado.floatValue();
	}

	public static Double parseNumeroDouble(String numero) {
		Number numeroParseado = parse(numero);

		return numeroParseado == null ? null : numeroParseado.doubleValue();
	}

	private static Number parse(String numero) {
		NumberFormat nf = DecimalFormat.getNumberInstance(new Locale("pt", "BR"));

		try {
			return nf.parse(numero);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getDiaSemanaPorExtenso(int diaSemana) {
		switch (diaSemana) {
		case 1:
			return "Domingo";
		case 2:
			return "Segunda-feira";
		case 3:
			return "Terça-feira";
		case 4:
			return "Quarta-feira";
		case 5:
			return "Quinta-feira";
		case 6:
			return "Sexta-feira";
		case 7:
			return "Sábado";
		default:
			return "Dia Inválido";
		}
	}

	public static int getDiaSemanaPorData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date ontem;
		try {
			ontem = sdf.parse(data);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(ontem);
			return gc.get(GregorianCalendar.DAY_OF_WEEK);
		} catch (ParseException e) {
		}

		return 0;
	}

	public static void createDirIfNotExists(String pathDir) {
		File file = new File(pathDir);
		if (!file.exists()) {
			file.setWritable(true);
			file.mkdir();
		}
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static String generateDate(int startYear, int endYear) {
		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(startYear, endYear);

		gc.set(Calendar.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Devolve os dois (ou três) primeiros nomes.
	 */
	public static String abreviarNome(String nome) {
		if (nome == null) {
			return "";
		}

		int pos1 = nome.indexOf(" ");
		int pos2 = -1;
		int pos3 = -1;

		// se achou o primeiro, procura o segundo
		if (pos1 > -1) {
			pos2 = nome.indexOf(" ", pos1 + 1);

			// se achou o segundo, verifica o tamanho (para não devolver JOSE DA ao invés de
			// JOSE DA SILVA)
			if (pos2 > -1) { // se for um nome com menos de 4 letras, deve incluir o terceiro nome

				if ((pos2 - pos1) <= 4) {
					pos3 = nome.indexOf(" ", pos2 + 1);

					// se achou o terceiro, devolve os três nomes
					if (pos3 > -1) {
						// retorna os três nomes
						return nome.substring(0, pos3);
					} else {
						// se não achou o terceiro, devolve todos
						return nome;
					}
				} else {
					return nome.substring(0, pos2);
				}
			} else {
				return nome;
			}
		} else {
			return nome;
		}

	}
}
