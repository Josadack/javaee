/**
 * Confirmação de exclução de contatos
 @author Josadaque ferreira 
 */
 
 function confirmar(idcon){
    let resposta = confirm("Confirma a exclução desse contato?")  
    if ( resposta === true ){
        //alert(idcon)
        window.location.href = "delete?idcon=" + idcon
 }
 }
