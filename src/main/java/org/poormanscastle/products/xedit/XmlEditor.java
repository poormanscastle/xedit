package org.poormanscastle.products.xedit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.commons.lang3.StringUtils;
import org.jaxen.JaxenException;

/**
 * Created by georg on 24.11.15.
 */
public class XmlEditor extends JFrame {

    public final static String HR = "----------------------------------------------------------";
    public final static String NL = "\n";

    private JTextArea xmlArea;
    private JTextArea xPathArea;
    private JTextArea outputArea;
    private JButton evaluateXpathButton;

    public XmlEditor() {
        super("XML Editor");
        setSize(1200, 1200);

        xmlArea = new JTextArea("XML AREA", 40, 120);
        getContentPane().add(new JScrollPane(xmlArea), BorderLayout.CENTER);

        JPanel xPathPanel = new JPanel();
        xPathArea = new JTextArea("XPATH AREA", 6, 120);
        xPathPanel.add(xPathArea);
        evaluateXpathButton = new JButton(new EvaluateXpathCommand());
        xPathPanel.add(evaluateXpathButton);
        getContentPane().add(xPathPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea("OUTPUT AREA", 6, 60);
        getContentPane().add(new JScrollPane(outputArea), BorderLayout.WEST);

        pack();
        setVisible(true);
    }

    public class EvaluateXpathCommand extends AbstractAction {

        public EvaluateXpathCommand() {
            putValue(Action.NAME, "Evaluate XPath");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                OMElement document = new StAXOMBuilder(new ByteArrayInputStream(xmlArea.getText().getBytes("UTF-8"))).getDocumentElement();
                AXIOMXPath xpath = new AXIOMXPath(xPathArea.getText());
                outputArea.append(StringUtils.join(HR, NL, xpath.evaluate(document)));
            } catch (XMLStreamException | UnsupportedEncodingException | JaxenException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new XmlEditor();
    }
}

