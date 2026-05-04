def calcular_imc(peso, altura):
    return peso / (altura ** 2)


def classificar_imc(imc):
    if imc < 18.5:
        return "Abaixo do peso"
    if imc < 25:
        return "Peso normal"
    if imc < 30:
        return "Sobrepeso"
    return "Obesidade"


def main():
    peso = float(input("Peso em kg: "))
    altura = float(input("Altura em metros: "))

    imc = calcular_imc(peso, altura)
    classificacao = classificar_imc(imc)

    print(f"IMC: {imc:.2f}")
    print(f"Classificacao: {classificacao}")


if __name__ == "__main__":
    main()
