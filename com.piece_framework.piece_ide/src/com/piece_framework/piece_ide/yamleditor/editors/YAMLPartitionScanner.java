package com.piece_framework.piece_ide.yamleditor.editors;

import java.util.Vector;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

/**
 * YAML �p�[�e�B�[�V�����X�L���i�[.
 * YAML �h�L�������g���Ӗ��̂���P�ʂɕ�������B<br>
 * YAML �ł̓\�[�X������3�ɕ�������B<br>
 * �EYAML �R�[�h<br>
 * �@�@�V�[�P���X��}�b�s���O�Ȃ�YAML�̈�ʃR�[�h�B<br>
 * �EYAML �R�����g<br>
 * �@�@"#"�ȉ���1�s�B<br>
 * �EYAML �I�[<br>
 * �@�@"..."�ȉ��̑S�s�B<br>
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.jface.text.rules.RuleBasedPartitionScanner
 * 
 */
public final class YAMLPartitionScanner extends RuleBasedPartitionScanner {
    
    /** YAML �I�[. */
    private static final String YAML_TERMINATE = "__yaml_TERMINATE";
    
    /** �p�[�e�B�[�V�����^�C�v�z��. */
    public static final String[] YAML_PARTITION_TYPES = new String[] { 
                                        YAML_TERMINATE };
    
    /** YAML �I�[�F. */
    private static final RGB YAML_TERMINATE_COLOR = new RGB(128, 128, 128);
    
    /** �p�[�e�B�[�V�����F�z��. */
    public static final RGB[] YAML_PARTITION_COLORS = new RGB[] { 
                                        YAML_TERMINATE_COLOR };
    
    private static YAMLPartitionScanner scanner;
    
    /**
     * �R���X�g���N�^.
     * �e�p�[�e�B�[�V�����̃��[�����쐬����B
     */
    private YAMLPartitionScanner() {
        
        Vector<IPredicateRule> rules = new Vector<IPredicateRule>();
        
        // �I�[
            // "..."�ȍ~�̑S�s
        rules.add(new MultiLineRule("...\n", "\0", 
                    new Token(YAML_TERMINATE), (char) 0 , true));
        
        IPredicateRule[] r = new IPredicateRule[1];
        setPredicateRules(rules.toArray(r));
    }
    
    /**
     * YAML �p�[�e�B�[�V�����X�L���i�[�̃C���X�^���X��Ԃ�.
     * 
     * @return YAML �p�[�e�B�V�����X�L���i�[
     */
    public static YAMLPartitionScanner getScanner() {
        if (scanner == null) {
            scanner = new YAMLPartitionScanner();
        }
        return scanner;
    }
}
