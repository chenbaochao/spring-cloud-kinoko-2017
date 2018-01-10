package ninja.cero.ecommerce.stock.client;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;

import ninja.cero.ecommerce.stock.domain.Stock;

public class StockClient {
	private static final String STOCK_URL = "http://stock-service";

	WebClient webClient;

	public StockClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<Stock> findAll() {
		return webClient.get().uri(STOCK_URL).retrieve().bodyToFlux(Stock.class).collectList().block();
	}

	public List<Stock> findByIds(Collection<Long> ids) {
		String idString = ids.stream().map(id -> id.toString()).collect(Collectors.joining(","));
		return webClient.get().uri(STOCK_URL + "/" + idString).retrieve().bodyToFlux(Stock.class).collectList().block();
	}

	public void keepStock(List<Stock> keeps) {
		webClient.post().uri(STOCK_URL).syncBody(keeps).exchange().block();
	}
}
