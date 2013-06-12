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
    println(vCard.write())

    // [ 'TEL' : 'HOME', 'TEL' : 'CELL ]
    def typeToSubType = [:]

    for (vCardType in vCard) {
        for (vCardSubType in vCardType.getSubTypes()) {
            
            def vCardTypeName = vCardType.getTypeName();
            
            if ("TYPE".equals(vCardSubType.key)) {
                
                def vCardSubTypeName = vCardSubType.value[0]
                
                if (typeToSubType.containsKey(vCardTypeName)) {
                    
                    def vCardSubTypeNames = typeToSubType[vCardTypeName]
                    
                    if (vCardSubTypeName in vCardSubTypeNames) {
                        println("Found duplicate type " + vCardTypeName + " with parameter " + vCardSubTypeName)
                    } else {
                        typeToSubType[vCardTypeName] = vCardSubTypeNames << vCardSubTypeName
                    }
                    
                } else {
                    
                    typeToSubType[vCardTypeName] = [ vCardSubTypeName ]
                    
                }
                
            }
            
        }
    }

    println(typeToSubType)
}

vCardReader.close()
