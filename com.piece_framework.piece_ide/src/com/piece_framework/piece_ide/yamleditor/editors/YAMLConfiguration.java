package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.Color;

/**
 * YAML �R���t�B�M�����[�V����.
 * YAML �G�f�B�^�[�̓�����J�X�^�}�C�Y����B
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.jface.text.source.SourceViewerConfiguration
 * 
 */
public class YAMLConfiguration extends SourceViewerConfiguration {
    private YAMLDoubleClickStrategy doubleClickStrategy;
    
    /**
     * ���[���������Ȃ��X�L���i�N���X.
     * �e�L�X�g�����݂̂�ݒ�\�ȃX�L���i�N���X�B
     * 
     * @author Hideharu Matsufuji
     * @version 0.1.0
     * @since 0.1.0
     * @see org.eclipse.jface.text.rules.BufferedRuleBasedScanner
     */
    static class SingleTokenScanner extends BufferedRuleBasedScanner {
        
        /**
         * �R���X�g���N�^.
         * �e�L�X�g������n���B
         * 
         * @param attribute �e�L�X�g����
         */
        public SingleTokenScanner(TextAttribute attribute) {
            setDefaultReturnToken(new Token(attribute));
        }
    }    
    
    /**
     * YAML �G�f�B�^�[���T�|�[�g����p�[�e�B�[�V�����^�C�v��Ԃ�.
     * 
     * @param sourceViewer �\�[�X�r���[
     * @return �p�[�e�B�V�����^�C�v
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration
     *          #getConfiguredContentTypes(
     *              org.eclipse.jface.text.source.ISourceViewer)
     */
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        int patternNum = YAMLPartitionScanner.YAML_PARTITION_TYPES.length + 1;
        String[] types = new String[patternNum];
        
        types[0] = IDocument.DEFAULT_CONTENT_TYPE;
        for (int i = 1; i < patternNum; i++) {
            types[i] = YAMLPartitionScanner.YAML_PARTITION_TYPES[i - 1];
        }
        
        return types;
    }
    
    /**
     * �_�u���N���b�N���̓�������肷������N���X��Ԃ�.
     * 
     * @param sourceViewer �\�[�X�r���[
     * @param contentType �R���e���g�^�C�v
     * @return �_�u���N���b�N���̓�������肷������N���X
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration
     *          #getDoubleClickStrategy(
     *              org.eclipse.jface.text.source.ISourceViewer, 
     *              java.lang.String)
     */
    public ITextDoubleClickStrategy getDoubleClickStrategy(
        ISourceViewer sourceViewer,
        String contentType) {
        
        if (doubleClickStrategy == null) {
            doubleClickStrategy = new YAMLDoubleClickStrategy();
        }
        return doubleClickStrategy;
    }
    
    /**
     * �G�f�B�^�[�̃v���[���e�[�V������ݒ�E�擾����.
     * 
     * @param sourceViewer �\�[�X�r���[
     * @return �v���[���e�[�V�������R���Z���[
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration
     *          #getPresentationReconciler(
     *              org.eclipse.jface.text.source.ISourceViewer)
     */
    public IPresentationReconciler getPresentationReconciler(
        ISourceViewer sourceViewer) {
        
        PresentationReconciler reconciler = new PresentationReconciler();
        
        DefaultDamagerRepairer dr;
        
        int partitionNum = YAMLPartitionScanner.YAML_PARTITION_TYPES.length;
        YAMLColorManager manager = YAMLColorManager.getColorManager();
        for (int i = 0; i < partitionNum; i++) {
            
            Color color = manager.getColor(
                            YAMLPartitionScanner.YAML_PARTITION_COLORS[i]);
            
            dr = new DefaultDamagerRepairer(
                    new SingleTokenScanner(new TextAttribute(color)));
            
            reconciler.setDamager(dr, 
                        YAMLPartitionScanner.YAML_PARTITION_TYPES[i]);
            reconciler.setRepairer(dr, 
                        YAMLPartitionScanner.YAML_PARTITION_TYPES[i]);
        }
        
        dr = new DefaultDamagerRepairer(YAMLCodeScanner.getScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        
        return reconciler;
    }

    
}