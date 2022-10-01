/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at http://sourceforge.net/projects/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Gunter Zeilinger, Huetteldorferstr. 24/10, 1150 Vienna/Austria/Europe.
 * Portions created by the Initial Developer are Copyright (C) 2002-2005
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * Gunter Zeilinger <gunterze@gmail.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.txt2dcmsr;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;


import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.util.UIDUtils;

/**
 * @author gunter zeilinger(gunterze@gmail.com)
 * @version $Revision: 12466 $ $Date: 2009-11-30 10:13:33 +0100 (Mo, 30 Nov 2009) $
 * @since Apr 1, 2006
 *
 */
public class Txt2DcmSR {
/*
    private static final String USAGE = 
        "txt2dcmsr [Options] <txtfile> <dcmsrfile>";
    private static final String DESCRIPTION = 
        "Encapsulate ASCII Text Document into DICOM Structured Report Document Object.\nOptions:";
    private static final String EXAMPLE = 
        "txt2dcmsr -c txt2dcmsr.cfg report.txt report.dcm\n" +
        "=> Convert Text Document report.txt into DICOM SR Object stored to " +
        "report.dcm using DICOM Attribute values specified in Configuration " +
        "file txt2dcmsr.cfg.";
    */
    private String transferSyntax = UID.ExplicitVRLittleEndian;
    private String charset = "ISO_IR 100";
    private boolean paragraphs = false;
    private Properties cfg = new Properties();

    public Txt2DcmSR() {
        try {
            cfg.load(Txt2DcmSR.class.getResourceAsStream("txt2dcmsr.cfg"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public final void setCharset(String charset) {
        this.charset = charset;
    }

    public final void setTransferSyntax(String transferSyntax) {
        this.transferSyntax = transferSyntax;
    }

    public final void setParagraphs(boolean para) {
        this.paragraphs = para;
    }

    
    public void convert(File txtFile, File dcmFile) throws IOException {
        DicomObject attrs = new BasicDicomObject();
        attrs.putString(Tag.SpecificCharacterSet, VR.CS, charset);
        for (Enumeration<?> en = cfg.propertyNames(); en.hasMoreElements();) {
            String key = (String) en.nextElement();
            if (key.length() > 0) {
                attrs.putString(Tag.toTagPath(key), null, cfg.getProperty(key));
            }
        }
        ensureVerifyingObserverIdCodeSeq(attrs);
        ensureUID(attrs, Tag.StudyInstanceUID);
        ensureUID(attrs, Tag.SeriesInstanceUID);
        ensureUID(attrs, Tag.SOPInstanceUID);
        Date now = new Date();
        ensureContenDateAndTime(attrs, now);
        attrs.putDate(Tag.InstanceCreationDate, VR.DA, now);
        attrs.putDate(Tag.InstanceCreationTime, VR.TM, now);
        DicomElement sq = attrs.get(Tag.toTagPath(cfg.getProperty("")));
        sq.getDicomObject().putBytes(Tag.TextValue, VR.UT, readBytes(txtFile));
        if (paragraphs) {
            splitParagraphs(sq);
        }
        
        attrs.initFileMetaInformation(transferSyntax);
        FileOutputStream fos = new FileOutputStream(dcmFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DicomOutputStream dos = new DicomOutputStream(bos);
        try {
            dos.writeDicomFile(attrs);
        } finally {
            dos.close();
        }
    }    

    private void splitParagraphs(DicomElement sq) {
        DicomObject item = sq.getDicomObject();
        String txt = item.getString(Tag.TextValue);
        StringTokenizer stk = new StringTokenizer(txt, "\r\n");
        String txt1 = stk.nextToken();
        item.remove(Tag.TextValue);
        while (stk.hasMoreTokens()) {
            String txt2 = stk.nextToken().trim();
            if (txt2.length() == 0) {
                continue;
            }
            DicomObject item2 = new BasicDicomObject();
            item.copyTo(item2);
            item2.putString(Tag.TextValue, VR.UT, txt2);
            sq.addDicomObject(item2);
        }
        item.putString(Tag.TextValue, VR.UT, txt1);
    }

    private byte[] readBytes(File txtFile) throws IOException {
        FileInputStream txtInput = new FileInputStream(txtFile);
        try {
            int txtLen = (int) txtFile.length();
            byte[] b = new byte[txtLen];
            new DataInputStream(txtInput).readFully(b);
            return b;
        } finally {
            txtInput.close();
        }
    }

    private void ensureContenDateAndTime(DicomObject attrs, Date now) {
        if (!attrs.containsValue(Tag.ContentDate)) {
            attrs.putDate(Tag.ContentDate, VR.DA, now);
        } else if (attrs.containsValue(Tag.ContentTime)) {
            return;
        }
        attrs.putDate(Tag.ContentTime, VR.TM, now);        
    }

    private void ensureVerifyingObserverIdCodeSeq(DicomObject attrs) {
        DicomElement sq =  attrs.get(Tag.VerifyingObserverSequence);
        if (sq != null && sq.hasItems()) {
            for (int i = 0, n = sq.countItems(); i < n; i++) {
                DicomObject item = sq.getDicomObject(i);
                if (!item.contains(Tag.VerifyingObserverIdentificationCodeSequence)) {
                    item.putNull(Tag.VerifyingObserverIdentificationCodeSequence, VR.SQ);
                }
            }
        }
    }

    private void ensureUID(DicomObject attrs, int tag) {
        if (!attrs.containsValue(tag)) {
            attrs.putString(tag, VR.UI, UIDUtils.createUID());
        }        
    }

    public static void main(String[] args) {
        try {
            Txt2DcmSR txt2dcmsr = new Txt2DcmSR();
                 txt2dcmsr.setTransferSyntax(UID.ImplicitVRLittleEndian);


            txt2dcmsr.setParagraphs(false); //te paràmetres?
         /*   List argList = null; //llista args;
            File txtFile = new File((String) argList.get(0));
            File dcmFile = new File((String) argList.get(1));
            long start = System.currentTimeMillis();
            txt2dcmsr.convert(txtFile, dcmFile);
            long fin = System.currentTimeMillis();
            System.out.println("Convert " + txtFile + " to " + dcmFile 
                    + " in " + (fin - start) +  "ms."); */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
