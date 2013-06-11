import ezvcard.Ezvcard
import ezvcard.VCard
import ezvcard.io.VCardReader

if (args.length != 1) {
    println("Please give one file name as parameter")
    return 1
}

vCardFile = new File(args[0])
vCardReader = new VCardReader(vCardFile)
VCard vCard = null
while ((vcard = vCardReader.readNext()) != null) {
    println(vcard.write())
}
vCardReader.close()
