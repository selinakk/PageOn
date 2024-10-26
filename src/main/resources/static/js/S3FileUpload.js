async function getURL(e){
    let file = e.files[0]
    let name = encodeURIComponent(file.name)
    let result = await fetch('/presigned-url?filename=' + name)
    result = await result.text()

    let img_name = await fetch(result, {
        method: 'PUT',
        body: e.files[0]
    })

    console.log(img_name.url.split("?")[0])
    if (img_name.ok) {
        document.querySelector('img').src = img_name.url.split("?")[0]
    }
}