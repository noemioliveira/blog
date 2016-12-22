package br.com.noemi.blog.util;

import java.text.Normalizer;

public class MyReplaceString {

	public static String formatarPermalink (String value){
		
		String link = value.trim();// pega string e retira  espaco em branco
		link.toLowerCase();// pega string e  coloca todas as letra em minusculo
		link = Normalizer.normalize(link, Normalizer.Form.NFD); //pega string e retira acentos e deixa  a letra
		link = link.replaceAll("\\s", "_");// encontra os espacos e substitue por _
		link = link.replaceAll("\\W", "_");// retira caracters especial como .,@,#, etc
		link = link.replaceAll("\\_+", "_");// se a string conter mais de um _(anderline) replaceAll vai deixar so um anderline(-) retirando os excessos
		
		return link;
	}
}
