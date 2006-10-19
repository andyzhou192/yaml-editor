package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * YAML �R���t�B�M�����[�V����.
 * YAML �G�f�B�^�[�̓�����J�X�^�}�C�Y����B
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.source.SourceViewerConfiguration
 * 
 */
public class YAMLConfiguration extends SourceViewerConfiguration {
    private YAMLDoubleClickStrategy doubleClickStrategy;
    private YAMLTagScanner tagScanner;
    private YAMLScanner scanner;
    private ColorManager colorManager;

    /**
     * �R���X�g���N�^.
     * 
     * @param cm �J���[�}�l�[�W���[
     */
    public YAMLConfiguration(ColorManager cm) {
        this.colorManager = cm;
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
        return new String[] {
            IDocument.DEFAULT_CONTENT_TYPE,
            YAMLPartitionScanner.YAML_COMMENT,
            YAMLPartitionScanner.YAML_MAPPING_KEY,
            YAMLPartitionScanner.YAML_MAPPING_VAL };
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
     * YAML �X�L���i�[���擾����.
     * 
     * @return YAML �X�L���i�[
     */
    protected YAMLScanner getYAMLScanner() {
        if (scanner == null) {
            scanner = new YAMLScanner(colorManager);
            scanner.setDefaultReturnToken(
                new Token(
                    new TextAttribute(
                        colorManager.getColor(IYAMLColorConstants.DEFAULT))));
        }
        return scanner;
    }
    
    /**
     * YAML �^�O�X�L���i�[���擾����.
     * 
     * @return YAML �^�O�X�L���i�[
     */
    protected YAMLTagScanner getYAMLTagScanner() {
        if (tagScanner == null) {
            tagScanner = new YAMLTagScanner(colorManager);
            // TODO:�^�O�X�L���i�[�H
            /*
            tagScanner.setDefaultReturnToken(
                new Token(
                    new TextAttribute(
                        colorManager.getColor(IYAMLColorConstants.TAG))));
            */
        }
        return tagScanner;
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
        
        // TODO:�p�[�e�B�[�V�����P�ʂ̃_���[�W���ƃ��y�A���̐ݒ�
        /*
        DefaultDamagerRepairer dr =
            new DefaultDamagerRepairer(getXMLTagScanner());
        reconciler.setDamager(dr, YAMLPartitionScanner.YAML_MAPPING_KEY);
        reconciler.setRepairer(dr, YAMLPartitionScanner.YAML_MAPPING_KEY);
        
        dr = new DefaultDamagerRepairer(getXMLScanner());
        reconciler.setDamager(dr, YAMLPartitionScanner.YAML_MAPPING_VAL);
        reconciler.setRepairer(dr, YAMLPartitionScanner.YAML_MAPPING_VAL);
        */
        DefaultDamagerRepairer dr;
        dr = new DefaultDamagerRepairer(getYAMLScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        NonRuleBasedDamagerRepairer ndr =
            new NonRuleBasedDamagerRepairer(
                new TextAttribute(
                    colorManager.getColor(IYAMLColorConstants.YAML_COMMENT)));
        reconciler.setDamager(ndr, YAMLPartitionScanner.YAML_COMMENT);
        reconciler.setRepairer(ndr, YAMLPartitionScanner.YAML_COMMENT);
        
        ndr = new NonRuleBasedDamagerRepairer(
                new TextAttribute(
                    colorManager.getColor(
                        IYAMLColorConstants.YAML_MAPPING_KEY)));
        reconciler.setDamager(ndr, YAMLPartitionScanner.YAML_MAPPING_KEY);
        reconciler.setRepairer(ndr, YAMLPartitionScanner.YAML_MAPPING_KEY);
        
        ndr = new NonRuleBasedDamagerRepairer(
                new TextAttribute(
                    colorManager.getColor(
                        IYAMLColorConstants.YAML_MAPPING_VAL)));
        reconciler.setDamager(ndr, YAMLPartitionScanner.YAML_MAPPING_VAL);
        reconciler.setRepairer(ndr, YAMLPartitionScanner.YAML_MAPPING_VAL);
        
        return reconciler;
    }

}