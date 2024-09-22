import React from 'react';

const facts = [
    {
        title: 'The First Computer Bug',
        description: 'In 1947, Grace Hopper found a moth causing issues in a Harvard Mark II computer, calling it a "bug."',
        image: 'https://upload.wikimedia.org/wikipedia/commons/3/3a/First_Computer_Bug%2C_1945.jpg'
    },
    {
        title: 'Moore\'s Law',
        description: 'Gordon Moore predicted that the number of transistors on a microchip would double every two years, shaping the future of computing power.',
        image: 'https://upload.wikimedia.org/wikipedia/commons/0/0f/Computer-transistors.jpg'
    },
    {
        title: 'The First 1GB Hard Drive',
        description: 'IBM introduced the first gigabyte hard drive in 1980, weighing over 500 pounds and costing $40,000.',
        image: 'https://upload.wikimedia.org/wikipedia/commons/e/ed/IBM3380.jpg'
    }
];

export default function FactSection() {
    return (
        <div className="fact-section">
            <style jsx>{`
                .fact-section {
                    margin: 40px 0;
                    padding: 20px;
                    border: 1px solid #ddd;
                    border-radius: 12px;
                    background-color: #fff;
                    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
                }
                .fact {
                    display: flex;
                    margin-bottom: 20px;
                    align-items: center;
                }
                .fact img {
                    width: 150px;
                    height: 150px;
                    border-radius: 12px;
                    margin-right: 20px;
                }
                .fact h3 {
                    margin: 0 0 10px;
                }
            `}</style>
            <h2>Did You Know?</h2>
            {facts.map((fact, index) => (
                <div className="fact" key={index}>
                    <img src={fact.image} alt={fact.title} />
                    <div>
                        <h3>{fact.title}</h3>
                        <p>{fact.description}</p>
                    </div>
                </div>
            ))}
        </div>
    );
}
