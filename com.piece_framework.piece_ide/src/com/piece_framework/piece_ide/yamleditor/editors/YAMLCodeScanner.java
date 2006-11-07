package com.piece_framework.piece_ide.yamleditor.editors;

import java.util.Vector;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.RGB;

/**
 * YAML �R�[�h�X�L���i�[.
 * YAML �̃R�[�h�̃��[�����`�E�ݒ肷��B<br>
 * <br>
 * �E�}�b�s���O(�n�b�V��)�L�[<br>
 * �E���p���ň͂܂ꂽ�Œ蕶����<br>
 * �EYAML�o�[�W�����w��<br>
 * �E�R�����g<br>
 * �E�V�[�P���X(�z��)��"-"<br>
 * �E�h�L�������g��؂��"---"<br>
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.jface.text.rules.RuleBasedScanner
 */
public final class YAMLCodeScanner extends RuleBasedScanner {
    
    /**
     * ���[�h�F���p.
     * 
     * @author Hideharu Matsufuji
     * @version 0.1.0
     * @since 0.1.0
     * @see org.eclipse.jface.text.rules.IWordDetector
     */
    private static class YAMLWordDetector implements IWordDetector {
        
        /**
         * �w�肳�ꂽ�L�����N�^�����[�h�̈ꕔ�Ƃ��ĔF�����邩��Ԃ�.
         * 
         * @param c �`�F�b�N�ΏۃL�����N�^
         * @return ���茋��
         * @see org.eclipse.jface.text.rules.IWordDetector#isWordPart(char)
         */
        public boolean isWordPart(char c) {
            return Character.isLetter(c) || c == '-';
        }

        /**
         * �w�肳�ꂽ�L�����N�^�����[�h�̐擪�Ƃ��ĔF�����邩��Ԃ�.
         * 
         * @param c �`�F�b�N�ΏۃL�����N�^
         * @return ���茋��
         * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
         */
        public boolean isWordStart(char c) {
            return Character.isLetter(c) || c == '-';
        }
    }
    
    /**
     * �󔒔F���p.
     * 
     * @author Hideharu Matsufuji
     * @version 0.1.0
     * @since 0.1.0
     * @see org.eclipse.jface.text.rules.IWhitespaceDetector
     */
    private static class YAMLWhitespaceDetector implements IWhitespaceDetector {
        
        /**
         * �w�肳�ꂽ�L�����N�^���󔒂Ƃ��ĔF�����邩��Ԃ�.
         * 
         * @param c �`�F�b�N�ΏۃL�����N�^
         * @return ���茋��
         * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
         */
        public boolean isWhitespace(char c) {
            return Character.isWhitespace(c);
        }
    }
    
    /**
     * �}�b�s���O(�n�b�V��)�L�[���[��.
     * �}�b�s���O(�n�b�V��)�̃L�[�͈ȉ��̃��[���Ŕ��f����B<br>
     * �E�A���t�@�x�b�g�A���p��(')�A��d���p��(")�̂����ꂩ�Ŏn�܂�B<br>
     * �E���[�h�̒��オ�R����(:)�ł���B<br>
     * �E�R����(:)�̒��オ���p�X�y�[�X���͉��s�ł���B<br>
     * 
     * @author Hideharu Matsufuji
     * @version 0.1.0
     * @since 0.1.0
     * @see org.eclipse.jface.text.rules.IRule
     */
    private static class YAMLMappingKeyRule implements IRule {
        
        private IToken token;
        
        /**
         * �R���X�g���N�^.
         * 
         * @param t ���[���ɍ��v�����Ƃ��ɓK�p����g�[�N���B
         */
        public YAMLMappingKeyRule(IToken t) {
            this.token = t;
        }
        
        /**
         * �}�b�s���O(�n�b�V��)�L�[���[���ɍ��v���邩���肷��.
         * �}�b�s���O(�n�b�V��)�L�[���[���ɍ��v�����ꍇ�͎w�肳�ꂽ�g�[�N����Ԃ��B
         * ���v���Ȃ��ꍇ��Token.UNDEFINED��Ԃ��B
         * 
         * @param scanner ����Ώۂ̃L�����N�^�����o���X�L���i
         * @return ���[���ɓK�p�����g�[�N���B
         * @see org.eclipse.jface.text.rules.IRule
         *          #evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
         */
        public IToken evaluate(ICharacterScanner scanner) {
            IToken retToken = Token.UNDEFINED;
            
            char c = (char) scanner.read();
            
            if (Character.isLetter(c) || c == '\"' || c == '\'') {
                char bc = '\0';
                char quatation = '\0';
                int count = 1;
                
                // ���p���Ŏn�܂�ꍇ�͂�����擾���Ă���
                if (c == '\"' || c == '\'') {
                    quatation = c;
                }
                do {
                    bc = c;
                    c = (char) scanner.read();
                    count++;
                    
                    // ���p���������ꍇ�̓��Z�b�g
                    if (c == quatation) {
                        quatation = '\0';
                    }
                    // ���p���ň͂܂ꂽ���Ŕ��p�X�y�[�X�̏ꍇ��
                    // ������u�����āA�I�����Ȃ��悤�ɂ���
                    if (quatation != '\0' && c == ' ') {
                        c = '_';
                    }
                } while (Character.isDefined(c) && c != ' ' && c != '\n');
                
                // ':'�̂��Ƃ��X�y�[�X�����s�Ȃ�����ɍ��v
                if (bc == ':') {
                    retToken = token;
                } else {
                    for (int i = 0; i < count; i++) {
                        scanner.unread();
                    }
                }
            } else {
                scanner.unread();
            }
            
            return retToken;
        }
        
    }
    
    /**
     * YAML�o�[�W�����w�胋�[��.
     * YAML�o�[�W�����w��͈ȉ��̃��[���Ŕ��f����B<br>
     * �E"--- %YAML"�Ŏn�܂�B<br>
     * �E"--- %YAML"����ɂЂƂ��p�X�y�[�X���͂���ŁA���̔��p�X�y�[�X<br>
     * �@���͉��s�܂ł��o�[�W�����Ƃ���B<br>
     * �E"--- %YAML"����ɔ��p�X�y�[�X���Ȃ��ꍇ�́A���̔��p�X�y�[�X�܂�<br>
     * �@��ΏۂƂ���B<br>
     * <br>
     * ��F<br>
     * �@"--- %YAML 1.1  # YAML�o�[�W�����w��"<br>
     * �@�@"--- %YAML 1.1"��YAML�o�[�W�����w�胋�[���ƂȂ�B<br>
     * <br>
     * �@"--- %YAML2.0 # YAML Version"<br>
     * �@�@"--- %YAML2.0"��YAML�o�[�W�����w�胋�[���ƂȂ�B<br>
     * 
     * @author Hideharu Matsufuji
     * @version 0.1.0
     * @since 0.1.0
     * @see org.eclipse.jface.text.rules.IRule
     */
    private static class YAMLVersionRule implements IRule {
            
        private IToken token;
        private static final String VERSION = "--- %YAML";
        
        /**
         * �R���X�g���N�^.
         * 
         * @param t ���[���ɍ��v�����Ƃ��ɓK�p����g�[�N���B
         */
        public YAMLVersionRule(IToken t) {
            this.token = t;
        }

        /**
         * YAML�o�[�W�����w�胋�[���ɍ��v���邩���肷��.
         * YAML�o�[�W�����w�胋�[���ɍ��v�����ꍇ�͎w�肳�ꂽ�g�[�N����Ԃ��B
         * ���v���Ȃ��ꍇ��Token.UNDEFINED��Ԃ��B
         * 
         * @param scanner ����Ώۂ̃L�����N�^�����o���X�L���i
         * @return ���[���ɓK�p�����g�[�N���B
         * @see org.eclipse.jface.text.rules.IRule
         *          #evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
         */
        public IToken evaluate(ICharacterScanner scanner) {
            
            char[] version = VERSION.toCharArray();
            
            IToken retToken = Token.UNDEFINED;
            
            char c = (char) scanner.read();
            boolean check = true;
            int count = 1;
            
            for (int i = 0; i < version.length; i++) {
                if (version[i] != c) {
                    check = false;
                    break;
                }
                c = (char) scanner.read();
                count++;
            }
            if (check) {
                // �o�[�W�����ԍ��܂ł�ΏۂƂ���
                if (c == ' ') {
                    c = (char) scanner.read();
                }
                do {
                    c = (char) scanner.read();
                } while (Character.isDefined(c) && c != ' ' && c != '\n');
                
                retToken = token;
                scanner.unread();
                
            } else {
                for (int i = 0; i < count; i++) {
                    scanner.unread();
                }
            }
            
            return retToken;
        }
        
        
    }

    /** YAML �}�b�s���O(�L�[)�F. */
    private static final RGB YAML_MAPPING_KEY_COLOR = new RGB(10, 200, 10);
    /** YAML �Œ蕶����F. */
    private static final RGB YAML_STRING_COLOR = new RGB(0, 0, 255);
    /** YAML �o�[�W�����w��F. */
    private static final RGB YAML_VERSION_COLOR = new RGB(0, 150, 150);
    /** YAML �R�����g�F. */
    private static final RGB YAML_COMMENT_COLOR = new RGB(0, 100, 200);
    /** YAML �V�[�P���X(�z��)�F. */
    private static final RGB YAML_SEQUENCE_COLOR = new RGB(0, 0, 255);
    /** YAML �h�L�������g��؂�F. */
    private static final RGB YAML_DOC_SEPARATOR_COLOR = new RGB(255, 0, 0);
    
    /** �f�t�H���g�F. */
    private static final RGB YAML_DEFAULT_COLOR = new RGB(0, 0, 0);
    
    private static YAMLCodeScanner codeScanner;
    
    /**
     * �R���X�g���N�^.
     * ���[����`���s���B
     */
    private YAMLCodeScanner() {
        
        Vector<IRule> rules = new Vector<IRule>();
        YAMLColorManager manager = YAMLColorManager.getColorManager();
        
        IToken defaultToken = new Token(new TextAttribute(
                manager.getColor(YAML_DEFAULT_COLOR)));
        
        IToken keyToken = new Token(
                new TextAttribute(manager.getColor(YAML_MAPPING_KEY_COLOR)));
        IToken stringToken = new Token(
                new TextAttribute(manager.getColor(YAML_STRING_COLOR)));
        IToken versionToken = new Token(
                new TextAttribute(manager.getColor(YAML_VERSION_COLOR)));
        IToken commentToken = new Token(
                new TextAttribute(manager.getColor(YAML_COMMENT_COLOR)));
        IToken sequenceToken = new Token(
                new TextAttribute(manager.getColor(YAML_SEQUENCE_COLOR)));
        IToken docSeparatorToekn = new Token(
                new TextAttribute(manager.getColor(YAML_DOC_SEPARATOR_COLOR)));
        
        WordRule wordRule = new WordRule(new YAMLWordDetector(), defaultToken);
        
        // �}�b�s���O(�L�[)
            // ":"���O
        rules.add(new YAMLMappingKeyRule(keyToken));
        // �Œ蕶����
        rules.add(new SingleLineRule("\"", "\"", stringToken));
        rules.add(new SingleLineRule("\'", "\'", stringToken));
        // �o�[�W�����w��
            // "--- %YAML"�ȍ~��1�s
        rules.add(new YAMLVersionRule(versionToken));
        // �R�����g
            // "#"�ȍ~��1�s
        rules.add(new EndOfLineRule("#", commentToken));
        // �V�[�P���X(�z��)
            // "-"�̂�
        wordRule.addWord("-", sequenceToken);
        // �h�L�������g��؂�
            // "---"�̂�
        wordRule.addWord("---", docSeparatorToekn);
        rules.add(wordRule);
        
        // �󔒃��[����ݒ�
        rules.add(new WhitespaceRule(new YAMLWhitespaceDetector()));
        
        IRule[] r = new IRule[1];
        setRules(rules.toArray(r));
    }
    
    /**
     * YAML �R�[�h�X�L���i�[�̃C���X�^���X��Ԃ�.
     * 
     * @return YAML �R�[�h�X�L���i�[.
     */
    public static YAMLCodeScanner getScanner() {
        if (codeScanner == null) {
            codeScanner = new YAMLCodeScanner();
        }
        return codeScanner;
    }
    
}

