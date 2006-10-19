package com.piece_framework.piece_ide.yamleditor.editors;


import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * YAML �^�O�X�L���i�[.
 * YAML�^�O�̃��[�����`����B
 * TODO:"�^�O�X�L���i�["�Ƃ������O�͂ӂ��킵���Ȃ������B
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.RuleBasedScanner
 * 
 */
public class YAMLTagScanner extends RuleBasedScanner {
    
    // ��`����郋�[����
    private static final int RULE_NUM = 3;
    
    /**
     * �R���X�g���N�^.
     * YAML�̃��[�����`����B
     * 
     * @param colorManager �J���[�}�l�[�W���[
     */
    public YAMLTagScanner(ColorManager colorManager) {
        IToken string =
            new Token(
                new TextAttribute(
                        colorManager.getColor(IYAMLColorConstants.STRING)));

        IRule[] rules = new IRule[RULE_NUM];

        // �_�u���N�H�[�e�[�V�������[��
        rules[0] = new SingleLineRule("\"", "\"", string, '\\');
        // �V���O���N�H�[�e�[�V�������[��
        rules[1] = new SingleLineRule("'", "'", string, '\\');
        // ��ʓI�ȃX�y�[�X���[��
        rules[2] = new WhitespaceRule(new YAMLWhitespaceDetector());

        setRules(rules);
    }
}
