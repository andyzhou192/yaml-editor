package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * YAML �X�L���i�[.
 * YAML�w�b�_�[�̃��[�����`����B
 * TODO:�w�b�_�[�͂Ȃ��̂ŁA���̃N���X���̕s�v�H
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.RuleBasedScanner
 * 
 */
public class YAMLScanner extends RuleBasedScanner {

    /**
     * �R���X�g���N�^.
     * YAML�w�b�_�[�̃��[�����`����B
     * 
     * @param colorManager �J���[�}�l�[�W���[
     */
    public YAMLScanner(ColorManager colorManager) {
        IToken procInstr =
            new Token(
                new TextAttribute(
                        colorManager.getColor(IYAMLColorConstants.PROC_INSTR)));

        IRule[] rules = new IRule[2];
        // "<?"�`"?>"���[��
        rules[0] = new SingleLineRule("<?", "?>", procInstr);
        // ��ʓI�ȃX�y�[�X���[��
        rules[1] = new WhitespaceRule(new YAMLWhitespaceDetector());

        setRules(rules);
    }
}
