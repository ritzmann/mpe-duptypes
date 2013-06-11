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

    // [ 'TEL' : [ 'HOME', 'CELL' ] ]
    def typeToSubTypeList = [:]

    def i = vCard.iterator()
    while (i.hasNext()) {
        
        def vCardType = i.next()
        def vCardSubTypes = vCardType.getSubTypes()
        
        if (!vCardSubTypes.isEmpty()) {
            if (typeToSubTypeList.containsKey(vCardType.getTypeName())) {
                // TODO append all subtypes to existing list
            } else {
                // TODO add all subtypes, not just first one
                typeToSubTypeList[vCardType.getTypeName()] = [vCardType.getSubTypes().getType()]
            }
        }
        
    }

    println(typeToSubTypeList)
}

vCardReader.close()
