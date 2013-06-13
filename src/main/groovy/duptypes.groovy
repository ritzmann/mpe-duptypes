@Grab(group='com.googlecode.ez-vcard', module='ez-vcard', version='0.8.1')
import ezvcard.Ezvcard
import ezvcard.VCard
import ezvcard.io.VCardReader

if (args.length != 1) {
    println("Please give one file name as parameter")
    return 1
}

def vCardFile = new File(args[0])
def vCardReader = new VCardReader(vCardFile)

while ((vCard = vCardReader.readNext()) != null) {

    // [ 'TEL' : [ 'HOME', 'CELL' ] ]
    def typeToSubType = [:]

    for (vCardType in vCard) {
        for (vCardSubType in vCardType.getSubTypes()) {
            
            def vCardTypeName = vCardType.getTypeName();
            
            if ("TYPE".equals(vCardSubType.key)) {
                
                def vCardSubTypeName = vCardSubType.value[0]
                
                if (typeToSubType.containsKey(vCardTypeName)) {
                    
                    def vCardSubTypeNames = typeToSubType[vCardTypeName]
                    
                    if (vCardSubTypeName in vCardSubTypeNames) {
                        if (vCard.getStructuredName().getGiven() != null || vCard.getStructuredName().getFamily() != null) {
                            println(vCard.getStructuredName().getGiven() + " " + vCard.getStructuredName().getFamily() + 
                                    ": Duplicate type " + vCardTypeName + " with parameter " + vCardSubTypeName)
                        } else {
                            println(vCard.getOrganization().getValues()[0] + 
                                    ": Duplicate type " + vCardTypeName + " with parameter " + vCardSubTypeName)
                        }
                    } else {
                        typeToSubType[vCardTypeName] = vCardSubTypeNames << vCardSubTypeName
                    }
                    
                } else {
                    
                    typeToSubType[vCardTypeName] = [ vCardSubTypeName ]
                    
                }
            }
        }
    }
}

vCardReader.close()
