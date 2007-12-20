
package org.dianexus.triceps;

import org.apache.oro.text.*;
import org.apache.oro.text.awk.*;
import org.apache.oro.text.regex.*;
import java.util.logging.*;

/**
  Interface to regular expression matching via jakarta ORO project.  Only used within Functions class.
  XXX:  Should this be replaced with new Java regex functions?
  
  @see Functions#REGEX_MATCH
*/
class InputValidator implements VersionIF {
  static Logger logger = Logger.getLogger("org.dianexus.triceps.InputValidator");
 	static final PatternCompiler compiler = new Perl5Compiler();
	static final PatternMatcher matcher = new Perl5Matcher();
	static final InputValidator	NULL	= new InputValidator();
	static final String VALIDATION_TYPE_PERL	= "PERL5";
	
	private String pattern = null;
	private boolean	valid = false;
	private String err = null;
	private Pattern compiledPattern = null;
	
	
	/** private constructors */
	private InputValidator() {
		// always returns true -- not a pattern
		this.pattern = ".*";
	}
	
	private InputValidator(String p)	{ 
		this.pattern = p;
		compile();
	}
		
	
	static InputValidator getInstance(String p) {
		// does it begin with the record delimiter? 
		InputValidator iv = NULL;
		
		if (p == null || !p.startsWith(VALIDATION_TYPE_PERL)) {
			return NULL;	// not a regex field - ignore it.
		}
		
		iv = new InputValidator(p.substring(VALIDATION_TYPE_PERL.length()));
		return iv;
	}
	
	boolean isValid() { return valid; }
	String getErrors() { return err; }
	boolean hasErrors() { return (err != null); }
	String getPattern() { return pattern; }
	boolean isNull() { return (this == NULL); }
	
	/** test whether the pattern is valid */
	private void compile() {
		try {
			compiledPattern = compiler.compile(pattern,Perl5Compiler.DEFAULT_MASK);	// Perl5Compiler.CASE_INSENSITIVE_MASK
			valid = true;
		}
		catch (MalformedPatternException e) {
			// show error
			logger.log(Level.SEVERE,"",e);			
			err = "Malformed Regular Expression '" + e.getMessage() + "'";
			valid = false;
		}
	}
	
	/** test whether string s matches the pattern */
	boolean isMatch(String s) {
		if (this == NULL) {
			return true;
		}
		if (!isValid()) {
			return false;	// should really return an error
		}
		if (matcher.matches(s,compiledPattern)) {
			return true;
		}
		else {
			return false;
		}
	}
}
