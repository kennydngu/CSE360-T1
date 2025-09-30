package emailRecognizer;

public class EmailRecognizer {
	public static String EmailRecognizerErrorMessage = "";	
	public static String EmailRecognizerInput = "";			
	public static int EmailRecognizerIndexofError = -1;		
	private static int state = 0;						 
	private static int nextState = 0;					// The next state value
	private static boolean finalState = false;			// Is this state a final state?
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;	
	
	private static boolean localPart = true;
	private static boolean seenAt = false;
    private static boolean lastWasDot = false;
	
	private static int localSize = 0;
	private static int labelSize = 0;
	private static int domainSize = 0;
	private static int topDomainSize = 0;

	
	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;
		}
	}
	
	
	private static void displayDebuggingInfo() {
		// Display the current state of the FSM as part of an execution trace
		if (currentCharNdx >= inputLine.length())
			// display the line with the current state numbers aligned
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
					((finalState) ? "       F   " : "           ") + "None");
		else
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
				((finalState) ? "       F   " : "           ") + "  " + currentChar + " " + 
				((nextState > 99) ? "" : (nextState > 9) || (nextState == -1) ? "   " : "    ") + 
				nextState + "     " + (localSize + domainSize + topDomainSize + 2));
	}
	
	public static boolean validEmailChar (char c, boolean inLocal) {
		if(Character.isLetterOrDigit(c)) {
			return true;
		}
		
		if(inLocal) {
			return (c == '.' || c == '_' || c == '%' || c == '+' || c == '-');
		}
		else {
			return(c == '.' || c == '-');
		}
			
	}
	
	private static boolean isAlpha(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
	
	public static String checkForValidEmail(String input) {
		if(input.length() <= 0) {
			EmailRecognizerIndexofError = 0;	
			return "\n*** ERROR *** The input is empty";
		}
		
		
		state = 0;							// This is the FSM state number
		inputLine = input;					// Save the reference to the input line as a global
		currentCharNdx = 0;					// The index of the current character
		currentChar = input.charAt(0);		// The current character from above indexed position
        nextState = -1;
        finalState = false;
        EmailRecognizerInput = input;
        running = true;

        localPart = true;
        seenAt = false;
        lastWasDot = false;

        localSize = 0;
        domainSize = 0;
        labelSize = 0;
        topDomainSize = 0;
		
		EmailRecognizerInput = input;	
		running = true;						
		nextState = -1;			
		
		System.out.println("\nCurrent Final Input  Next  Date\nState   State Char  State  Size");
		
		

		// The Finite State Machines continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state
		while (running) {
			// The switch statement takes the execution to the code for the current state, where
			// that code sees whether or not the current character is valid to transition to a
			// next state
			switch (state) {
			case 0: 
				// State 0 has 1 valid transition that is addressed by an if statement.
				
				// The current character is checked against A-Z, a-z. If any are matched
				// the FSM goes to state 1
				
				// A-Z, a-z -> State 1
				if (currentChar == '.') { running = false; break; } // no leading dot
				if (validEmailChar(currentChar, localPart))	// Check for a-z
						 {	
					nextState = 1;
					localSize++;
				}
				
				else 
					running = false;
				
				// The execution of this state is finished
				break;
			
			case 1: 
				// State 1 has two valid transitions, 
				//	1: a A-Z, a-z, 0-9 that transitions back to state 1
				//  2: an @ symbol that transitions to state 2 

				
				if (validEmailChar(currentChar, localPart)) {	// Check for a-z	
					// no consecutive dots
					if (currentChar == '.' && lastWasDot) { running = false; break; }
						nextState = 1;
						localSize++;
						lastWasDot = (currentChar == '.'); 
					} 
				// . -> State 2
				else if (currentChar == '@') {
					if (seenAt || localSize == 0 || lastWasDot) {running = false; break;}
					seenAt = true;
					localPart = false;
					lastWasDot = false;
					nextState = 2;
					break;
				}				
				// If it is none of those characters, the FSM halts
				else
					running = false;
				
				if (localSize > 64)
					running = false;
				
				break;			
				
			case 2: 
				// State 2 deals with a character after an @ 
				
				// A-Z, a-z, 0-9 -> State 1
				localPart = false;
				if (domainSize == 0) { // if this is the first char of the domain
					if (!Character.isLetterOrDigit(currentChar)) { running = false; break; }
                    nextState = 2;
                    domainSize++;
                    labelSize = 1;
                    lastWasDot = false;
                    if (domainSize > 253) { running = false; }
                    break;
				}
				if (currentChar == '.') {
					if (lastWasDot) {running = false; break;}	  // no consecutive dots
					if (labelSize == 0) {running = false; break;} // empty label
					
					char prev = inputLine.charAt(currentCharNdx - 1);	// cannot end with a -
                    if (prev == '-') { running = false; break; }
                    
                    // next: TLD state
                    nextState = 3;
                    domainSize++;            
                    topDomainSize = 0;       // start counting TLD
                    lastWasDot = true;
                    if (domainSize > 253) { running = false; }
                    break;
				}
				
				if (Character.isLetterOrDigit(currentChar) || currentChar == '-') {
                    nextState = 2;
                    domainSize++;
                    labelSize++;
                    lastWasDot = false;
                    if (domainSize > 253) { running = false; }
                } else {
                    running = false;
                }
				break;		
				
			case 3:
				// State 3 deals with the top-level domain, that part after the . (.com, .org)
				if (isAlpha(currentChar))	// Check for a-z
						 {	
					nextState = 3;
					// Count the character 
					topDomainSize++;
					lastWasDot = false;
					if (topDomainSize > 24) { running = false; }
				} else // Anything else is invalid
					running = false;
				if (topDomainSize > 24)  // max size of the topDomainSize
					running = false;
				
				
			}
			
			if (running) {
				displayDebuggingInfo();
				// When the processing of a state has finished, the FSM proceeds to the next
				// character in the input and if there is one, it fetches that character and
				// updates the currentChar.  If there is no next character the currentChar is
				// set to a blank.
				moveToNextCharacter();

				// Move to the next state
				state = nextState;
				
				// Is the new state a final state?  If so, signal this fact.
				if (state == 3) finalState = true;

				// Ensure that one of the cases sets this to a valid value
				nextState = -1;
			}
			// Should the FSM get here, the loop starts again
	
		}
		displayDebuggingInfo();
		
		System.out.println("The loop has ended.");
		
		// When the FSM halts, we must determine if the situation is an error or not.  That depends
		// of the current state of the FSM and whether or not the whole string has been consumed.
		// This switch directs the execution to separate code for each of the FSM states and that
		// makes it possible for this code to display a very specific error message to improve the
		// user experience.
		EmailRecognizerIndexofError = currentCharNdx;	// Set index of a possible error;
		EmailRecognizerErrorMessage = "\n*** ERROR *** ";
		
		// The following code is a slight variation to support just console output.
		
		int totalLen = localSize + 1 /*@*/ + domainSize + 1 /*.*/ + topDomainSize;
		switch (state) {
		
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			EmailRecognizerErrorMessage += "Email must start with A-Z, or a-z.\n";
			return EmailRecognizerErrorMessage;

		case 1:
			if (localSize == 0) {
		        EmailRecognizerErrorMessage += "Email is missing the local part before '@'.\n";
		        return EmailRecognizerErrorMessage;
		    }
		    if (lastWasDot) {
		        EmailRecognizerErrorMessage += "Local part cannot end with a dot.\n";
		        return EmailRecognizerErrorMessage;
		    }
		    if (!seenAt) {
		        EmailRecognizerErrorMessage += "Email must contain an '@' separating local and domain.\n";
		        return EmailRecognizerErrorMessage;
		    }
		    if (localSize > 64) {
		        EmailRecognizerErrorMessage += "Local part must be no more than 64 characters.\n";
		        return EmailRecognizerErrorMessage;
		    }
		    EmailRecognizerErrorMessage += "Invalid character in local part.\n";
		    return EmailRecognizerErrorMessage;

			

		case 2:
			// State 2 is not a final state, so we can return a very specific error message
			EmailRecognizerErrorMessage +=
				"Email must require a domain extension (e.g. .com, .org, .net, .edu, etc.).\n";
			return EmailRecognizerErrorMessage;
		case 3:
			// State 3 is a final state.  Check to see if the email length is valid.  If so we
			// we must ensure the whole string has been consumed.
			if (topDomainSize < 2) {
				// topdomain is too short (should be at least 2 letters, .com, .org,.me)
				EmailRecognizerErrorMessage += "Top-level domain must have at least 2 characters.\n";
				return EmailRecognizerErrorMessage;
			}
			if (localSize < 5) {
			    EmailRecognizerErrorMessage += "Local part must have at least 5 characters.\n";
			    return EmailRecognizerErrorMessage;
			}
			
			else if (totalLen > 254) { // Overall email length should be 64 chars max
				// Email is too long
				EmailRecognizerErrorMessage += 
					"Email must be no more than 254 characters.\n";
				return EmailRecognizerErrorMessage;
			}
			else if (currentCharNdx < input.length()) {
				// There are characters remaining in the input, so the input is not valid
				EmailRecognizerErrorMessage += 
					"Email contains invalid characters.\n";
				return EmailRecognizerErrorMessage;
			}
			else {
					// Email is valid
					EmailRecognizerIndexofError = -1;
					EmailRecognizerErrorMessage = "";
					return EmailRecognizerErrorMessage;
			}
			
			
		default:
			// This is for the case where we have a state that is outside of the valid range.
			// This should not happen
			return "";
		}
	}
	
}
