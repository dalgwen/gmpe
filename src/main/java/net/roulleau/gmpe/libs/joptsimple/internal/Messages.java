/*
 The MIT License

 Copyright (c) 2004-2016 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package net.roulleau.gmpe.libs.joptsimple.internal;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class Messages {

	private static Map<String, String> exceptionMessages;
	private static Map<String, String> helpFormatterMessages;

	private Messages() {
		throw new UnsupportedOperationException();
	}

	private static void init() {
		if (exceptionMessages == null || helpFormatterMessages == null) {
			exceptionMessages = new HashMap<String, String>();
			helpFormatterMessages = new HashMap<String, String>();

			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.IllegalOptionSpecificationException.message", "{0} is not a legal option character");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.MissingRequiredOptionsException.message", "Missing required option(s) {0}");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.MultipleArgumentsForOptionException.message", "Found multiple arguments for option {0}, but you asked for only one");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.OptionArgumentConversionException.message", "Cannot parse argument ''{0}'' of option {1}");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.OptionMissingRequiredArgumentException.message", "Option {0} requires an argument");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.UnavailableOptionException.message", "Option(s) {0} are unavailable given other options on the command line");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.UnconfiguredOptionException.message", "Option(s) {0} not configured on this parser");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.UnrecognizedOptionException.message", "{0} is not a recognized option");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.DateConverter.without.pattern.message", "Value [{0}] does not match date/time pattern");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.DateConverter.with.pattern.message", "Value [{0}] does not match date/time pattern [{1}]");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.RegexMatcher.message", "Value [{0}] did not match regex [{1}]");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.EnumConverter.message", "Value [{0}] is not one of [{1}]");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.file.existing.message", "File [{0}] does not exist");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.directory.existing.message", "Directory [{0}] does not exist");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.file.not.existing.message", "File [{0}] does already exist");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.file.overwritable.message", "File [{0}] is not overwritable");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.file.readable.message", "File [{0}] is not readable");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.PathConverter.file.writable.message", "File [{0}] is not writable");
			exceptionMessages.put("net.roulleau.gmpe.libs.joptsimple.util.InetAddressConverter.message", "Cannot convert value [{0}] into an InetAddress");
			
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.no.options.specified", "No options specified");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.non.option.arguments.header", "Non-option arguments:");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.option.header.with.required.indicator", "Option * required)");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.option.divider.with.required.indicator", "---------------------");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.option.header", "Option");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.option.divider", "------");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.description.header", "Description");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.description.divider", "-----------");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.BuiltinHelpFormatter.default.value.header", "default:");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.AlternativeLongOptionSpec.description", "Alternative form of long options");
			helpFormatterMessages.put( "net.roulleau.gmpe.libs.joptsimple.AlternativeLongOptionSpec.arg.description", "opt=value");
		}
	}

	public static String message(Locale locale, String bundleName, Class<?> type, String key, Object... args) {

		init();

		String template;
		if (bundleName.endsWith("ExceptionMessages")) {
			template = exceptionMessages.get(new String(type.getName() + "." + key));
		} else if (bundleName.endsWith("HelpFormatterMessages")) {
			template = helpFormatterMessages.get(type.getName() + "." + key);
		} else {
			throw new RuntimeException("dfgsdfg");
		}

		MessageFormat format = new MessageFormat(template);
		format.setLocale(locale);
		return format.format(args);
	}

}
